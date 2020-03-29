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
    @PostMapping("completeCheckOutForm")
    public String completeCheckOutForm(HttpServletRequest request, String cardType, String creditCard, String expiryDate, String billToFirstName, String billToLastName, String billAddress1, String billAddress2, String billCity, String billState, String billZip, String billCountry){
        Order order = new Order();

        List<LineItem>lineItems=new ArrayList<>();
        order.setLineItems(lineItems);

        int orderNumber = orderService.getNextId("ordernum");
        order.setOrderId(orderNumber);

        HttpSession httpSession = request.getSession();
        Account account = (Account) httpSession.getAttribute("account");
        Cart cart = (Cart) httpSession.getAttribute("cart");
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

        //插入订单=>更新数据库的库存值域
//        orderService.insertOrder(order);
        return "order/confirmCheckOutForm";

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
        HttpSession httpSession = request.getSession();
        Cart cart = new Cart();
        httpSession.setAttribute("cart",cart);

        // TODO 由于在第一个订单界面有问题，导致现在查表无法查到

//        Order order = orderService.getOrder(orderId);
        model.addAttribute("order",new Order());
        return "order/viewOrder";

    }

}
