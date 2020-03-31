package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.persistence.AccountMapper;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    /**
     * 登陆：跳转到登陆界面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "account/login";
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
            if (account != null) {
                httpSession.setAttribute("account", account);
                httpSession.setAttribute("loginError", null);
                httpSession.setAttribute("username",username);
                Cart cart = new Cart();
                httpSession.setAttribute("cart", cart);
                return "/catalog/main";
            } else {
                httpSession.setAttribute("loginError", 1);
                httpSession.setAttribute("account", null);
                return "/account/login";
            }
        }
        return "/catalog/main";
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
     * 注册完成:注册界面->跳转到主界面界面
     * 注意注册注册完成之后要把account和cart都放在session中，注册之后就不再去登陆，默认已经登陆了。
     * @param account
     * @param httpSession
     * @return
     */
    @PostMapping("/register_confirm")
    public String register_confirm(Account account, HttpSession httpSession) {

        // TODO 利用username验证是否已经存在

        // TODO 更正新增用户的bug

        account.setBannerOption(true);
        account.setListOption(true);
        account.setFavouriteCategoryId("DOGS");
//        System.out.println("check account info "+ account.getUsername()+" "+account.getPassword()+" "+account.getAddress1()+" "+account.getAddress2()+" "+account.getCity()+" "+account.getFirstName());
        accountService.insertAccount(account);
        httpSession.setAttribute("account",account);
        Cart cart = new Cart();
        httpSession.setAttribute("cart", cart);
        return "catalog/main";
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
        account.setLanguagePreference(languagePreference);
        account.setFavouriteCategoryId(favouriteCategoryId);
        account.setBannerOption(true);
        account.setListOption(true);
        accountService.update_info(account);
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
