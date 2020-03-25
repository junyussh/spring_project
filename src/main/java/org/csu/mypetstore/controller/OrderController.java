package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AccountService accountService;

    @GetMapping("viewOrderList")
    public String viewOrderList(String username, Model model, HttpSession httpSession){
        List<Order> orderList = orderService.getOrderListByUsername(username);
        Account account = accountService.getAccount(username);
        model.addAttribute("orderList",orderList);
        model.addAttribute("account",account);
        httpSession.setAttribute("username",username);
        return "order/orderList";
    }

}
