package org.csu.mypetstore.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.exception.ApiRequestException;
import org.csu.mypetstore.jwt.AuthenticationRequest;
import org.csu.mypetstore.jwt.AuthenticationResponse;
import org.csu.mypetstore.jwt.JwtUtil;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.HttpClientService;
import org.csu.mypetstore.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;
    /**
     * Login
     *
     * @return
     */
    @ApiOperation("Authenticate")
    @ApiResponses(value = {@ApiResponse(code=200,message = "Login Success"), @ApiResponse(code = 401, message = "Invalid username or password")})
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> authentication(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            System.out.println(authenticationRequest.getUsername());
            System.out.println(authenticationRequest.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new ApiRequestException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @PostMapping("/captcha")
    @ResponseBody
    public String verifyCaptcha(@RequestBody Map<String,String> captcha) throws JsonProcessingException {
        Map<String, String> data = new HashMap<>();
        String url = "https://www.google.com/recaptcha/api/siteverify";
        data.put("secret", "6LezCeYUAAAAAL7MJ1eoa-7r95JAE3hGRtu2pJG7");
        data.put("response", captcha.get("google_recaptcha_token"));
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(data, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("secret", "6LezCeYUAAAAAL7MJ1eoa-7r95JAE3hGRtu2pJG7")
                .queryParam("response", captcha.get("google_recaptcha_token"));
//        HttpEntity<?> entity = new HttpEntity<>(headers);
        return httpClientService.get(builder.toUriString());
    }

    /**
     * Query user by id
     * @param id
     * @return
     */
    @ApiResponses(value = {@ApiResponse(code=200,message = "Query Success")})
    @ApiOperation(value = "Query user info" , authorizations = {@Authorization(value = "Bearer")})
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "path", dataType = "String", name = "id", value = "User's userid", required = true) })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Map<String, Object> getUser(@PathVariable("id") String id) {
        Account account = accountService.getAccountByID(id);
        if (account == null) {
            throw new ApiRequestException("User id not found", HttpStatus.NOT_FOUND);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(account, Map.class);
        map.remove("password");
        return map;
    }

    /**
     * Register Interface
     * @ResponseStatus to set response's http status code
     * @ApiImplicitParams is fields' annotation for Swagger
     * @param account
     * @return
     */
    @ApiResponses(value = {@ApiResponse(code = 201 ,message = "User created")})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register", response = Account.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "username", value = "User's unique username", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "password", value = "User's password", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "email", value = "User's email", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "firstName", value = "User's first name", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "lastName", value = "User's last name", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "address1", value = "Address1", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "address2", value = "Address2", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "city", value = "User's city", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "state", value = "User's state", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "zip", value = "Zipcode", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "country", value = "Country user located", required = true, dataType = "string", paramType = "form"),
//            @ApiImplicitParam(name = "phone", value = "User's phone number", required = true, dataType = "string", paramType = "form"),
//    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Account addUser(@RequestBody Account account)
    {
        if (accountService.getAccountByUsername(account.getUsername()) != null) {
            throw new ApiRequestException("Username is duplicated", HttpStatus.BAD_REQUEST);
        }
        account.setStatus(false);
        System.out.println(account.getPassword());
        return accountService.insertAccount(account);
    }
    /**
     * 登陆验证：
     * 注意点：在验证成功之后要把购物车一块传进去
     * @param username
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/login_confirm")
    public String login_confirm(String username, String password, Model model, HttpSession httpSession) {
        if (username != null && password != null) {
            Account account = accountService.getAccount(username, password);
            System.out.println("username "+ username);
            System.out.println(account);
            if (account != null) {
                httpSession.setAttribute("account", account);
                httpSession.setAttribute("loginError", null);
                httpSession.setAttribute("username",username);
                Cart cart = new Cart();
                httpSession.setAttribute("cart", cart);
                return "catalog/main";
            } else {
                httpSession.setAttribute("loginError", 1);
                httpSession.setAttribute("account", null);
                return "account/login";
            }
        }
        return "catalog/main";
    }

    /**
     * 退出跳转到主界面
     * 把session中的购物车清空
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.setAttribute("account", null);
        httpSession.setAttribute("cart", null);
        return "catalog/main";
    }

    /**
     * 去注册：登陆界面->跳转到注册界面
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String register(Model model) {
        List<String> languageList = new ArrayList<String>();
        languageList.add("English");
        languageList.add("Chinese");
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("FISH");
        categoryList.add("DOGS");
        categoryList.add("CATS");
        categoryList.add("BIRDS");
        categoryList.add("REPTILES");
        model.addAttribute("languageList",languageList);
        model.addAttribute("categoryList", categoryList);
        return "account/register";
    }

    /**
     * 提交更改信息：提交完成之后界面跳回到主界面
     * TODO：跳转完成还留在当前界面，然后点击之后再跳回
     *
     * @param password
     * @param repeatedPassword
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     * @param address1
     * @param address2
     * @param city
     * @param state
     * @param zip
     * @param country
     * @param languagePreference
     * @param favouriteCategoryId
     * @param session
     * @return
     */
    @PostMapping("/update_info")
    public String update_info(String password, String repeatedPassword, String firstName, String lastName , String email, String phone, String address1, String address2, String city, String state, String zip, String country, String languagePreference, String favouriteCategoryId,HttpSession session) {

        // TODO 更正修改用户信息的bug

        Account account = (Account) session.getAttribute("account");
        if(password==repeatedPassword) account.setPassword(password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPhone(phone);
        account.setAddress1(address1);
        account.setAddress2(address2);
        account.setCity(city);
        account.setState(state);
        account.setZip(zip);
        account.setCountry(country);
//        accountService.update_info(account);
        return "catalog/main";
    }

    /**
     * 去查看个人信息：有主界面->跳转到信息界面
     * @param model
     * @return
     */
    @RequestMapping("/view_info")
    public String view_info(Model model){
        List<String> languageList = new ArrayList<String>();
        languageList.add("English");
        languageList.add("Chinese");
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("FISH");
        categoryList.add("DOGS");
        categoryList.add("CATS");
        categoryList.add("BIRDS");
        categoryList.add("REPTILES");
        model.addAttribute("languageList",languageList);
        model.addAttribute("categoryList", categoryList);
//        model.addAttribute("account",)

        return "account/update_info";
    }

}
