package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CatalogService catalogService;

    /**
     * 添加购物车：
     *     1、判断购物车是否为空=为空new一个
     *     2、判断添加物是否已在购物车，调用cart中方法
     *
     * @param itemId
     * @param httpSession
     * @return
     */
    @GetMapping("/addItemToCart")
    public String addItemToCart(String itemId, HttpSession httpSession){
        Cart cart = (Cart)httpSession.getAttribute("cart");
        if (cart == null){
            cart = new Cart();
        }
        if (cart.containsItemId(itemId)){
            cart.incrementQuantityByItemId(itemId);
        } else{
            boolean isInStock = catalogService.isItemInStock(itemId);
            Item item = catalogService.getItem(itemId);
            cart.addItem(item, isInStock);
        }
        httpSession.setAttribute("cart",cart);
        return "/catalog/cart";
    }

    /**
     * 在主界面直接点击查看购物车=>跳转到购物车界面
     * @param httpSession
     * @return
     */
    @GetMapping("/viewCart")
    public String viewCart(HttpSession httpSession){
        Cart cart = (Cart)httpSession.getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            httpSession.setAttribute("cart",cart);
        }
        return "/catalog/cart";
    }

    /**
     * 购物车界面移除某一项元素
     * @param cartItemId
     * @param httpSession
     * @return
     */
    @GetMapping("/removeCartItem")
    public String removeCartItem(String cartItemId,HttpSession httpSession){
        Cart cart = (Cart)httpSession.getAttribute("cart");
        cart.removeItemById(cartItemId);
        httpSession.setAttribute("cart",cart);
        return "/catalog/cart";
    }

    /**
     * 将购物车中所有元素一下全部清掉
     * @param httpSession
     * @return
     */
    @GetMapping("/clearAll")
    public String clearAll(HttpSession httpSession){
        Cart cart = (Cart)httpSession.getAttribute("cart");
        cart.getCartItemList().clear();
        httpSession.setAttribute("cart",cart);
        return "/catalog/cart";
    }

    /**
     * 购物完成进入结账界面=>新建账单页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/checkOut")
    public String checkOut(HttpServletRequest request, Model model){
        HttpSession httpSession = request.getSession();
        Account account=(Account)httpSession.getAttribute("user");
        return "order/checkOutForm";
    }
}
