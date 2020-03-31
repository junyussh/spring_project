package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.LineItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    /**
     * 在mycount界面下(update_info界面)点击查看order跳转到个人所有order的list
     * @param username
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/viewOrderList")
    public String viewOrderList(String username, Model model, HttpSession httpSession){
        List<Order> orderList = orderService.getOrderListByUsername(username);
        Account account = accountService.getAccount(username);
        model.addAttribute("orderList",orderList);
        model.addAttribute("account",account);
        httpSession.setAttribute("username",username);
        return "order/orderList";
    }

    /**
     * 处理订单传进来的情况
     * 接收前端传来的参数时，需要用@RequestParam对前端的数据进行对应，不然取到的数据为null、
     *
     * @param request
     * @param cardType
     * @param creditCard
     * @param expiryDate
     * @param billToFirstName
     * @param billToLastName
     * @param billAddress1
     * @param billAddress2
     * @param billCity
     * @param billState
     * @param billZip
     * @param billCountry
     * @return
     */
    @PostMapping("/completeCheckOutForm")
    public String completeCheckOutForm(HttpServletRequest request,
                                       @RequestParam("order.cardType")String cardType,
                                       @RequestParam("order.creditCard")String creditCard,
                                       @RequestParam("order.expiryDate")String expiryDate,
                                       @RequestParam("order.billToFirstName") String billToFirstName,
                                       @RequestParam("order.billToLastName")String billToLastName,
                                       @RequestParam("order.billAddress1")String billAddress1,
                                       @RequestParam("order.billAddress2")String billAddress2,
                                       @RequestParam("order.billCity")String billCity,
                                       @RequestParam("order.billState")String billState,
                                       @RequestParam("order.billZip")String billZip,
                                       @RequestParam("order.billCountry")String billCountry){
        System.out.println("printf the data from front"+billAddress1);
        String shippingAddress = request.getParameter("shippingAddressRequired");

//        Order order = new Order();
//
//        List<LineItem>lineItems=new ArrayList<>();
//        order.setLineItems(lineItems);
//
//        int orderNumber = orderService.getNextId("ordernum");
//        order.setOrderId(orderNumber);
//
//        HttpSession httpSession = request.getSession();
//        Account account = (Account) httpSession.getAttribute("account");
//        Cart cart = (Cart) httpSession.getAttribute("cart");
//        order.setAccountAndCart(account,cart);

        Order order=new Order();
        List<LineItem>lineItems=new ArrayList<>();

        HttpSession httpSession=request.getSession();
        Account account=(Account)httpSession.getAttribute("account");
        Cart cart=(Cart)httpSession.getAttribute("cart");
        int ordernum=orderService.getNextId("ordernum");
        order.setLineItems(lineItems);
        order.setOrderId(ordernum);
        order.setAccountAndCart(account,cart);

        order.setCardType(cardType);
        order.setCreditCard(creditCard);
        order.setExpiryDate(expiryDate);
        order.setBillToFirstName(billToFirstName);
        order.setBillToLastName(billToLastName);
        order.setBillAddress1(billAddress1);
        order.setBillAddress2(billAddress2);
        order.setBillCity(billCity);
        order.setBillState(billState);
        order.setBillZip(billZip);
        order.setBillCountry(billCountry);
        httpSession.setAttribute("order",order);


        if(shippingAddress==null){
//            System.out.println("@@@@@@@@@@@@@   "+order.getLineItems().get(0).getItem().getProduct());
            orderService.insertOrder(order);
            // TODO 跳转到最后的confirm界面有问题
            return "order/confirmCheckOutForm";
        }else{
            return "order/shippingForm";
        }
    }

    /**
     * 显示本次订单的界面，跳转到viewOrder页面
     * @param request
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/viewOrder")
    public String viewOrder(HttpServletRequest request,int orderId,Model model){
        System.out.println("here is presenting the webpage "+orderId);
        HttpSession httpSession = request.getSession();
        Cart cart = new Cart();
        httpSession.setAttribute("cart",cart);

        // TODO 由于在第一个订单界面有问题，导致现在查表无法查到 =>已解决

        Order order = orderService.getOrder(orderId);



        // TODO 在最后呈现页面的时候渲染列表时输出getItem就是空[对象为null]

        System.out.println("check the order 's lineitemlist = "+order.getLineItems().size()+"～～～～～～～～～～～~~~~~~~~~~");
        for (LineItem lineItem : order.getLineItems()){
            System.out.println(lineItem);
            System.out.println(lineItem.getItem());  //输出为null
            System.out.println("^^^^^^^");
//            System.out.println(lineItem.getItem().getProduct());
//            System.out.println(lineItem.getItem().getProduct().getProductId());
        }

        model.addAttribute("order",order);
        return "order/viewOrder";
    }

    @PostMapping("/confirmShippingForm")
    public String confirmShippingForm(HttpServletRequest request, String shipToFirstName, String shipToLastName, String shipAddress1, String shipAddress2, String shipCity, String shipState, String shipZip, String shipCountry){
        HttpSession httpSession = request.getSession();
        Order order=(Order)httpSession.getAttribute("order");
        order.setShipToFirstName(shipToFirstName);
        order.setShipToLastName(shipToLastName);
        order.setShipAddress1(shipAddress1);
        order.setBillAddress2(shipAddress2);
        order.setShipCity(shipCity);
        order.setShipState(shipState);
        order.setShipZip(shipZip);
        order.setShipCountry(shipCountry);
        orderService.insertOrder(order);
        // TODO 跳转到最后的confirm界面有问题
        return "order/comfirmCheckOutForm";

    }

}
