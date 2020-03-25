package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("cart")
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
        if (!cart.containsItemId(itemId)){
            cart.incrementQuantityByItemId(itemId);
        } else{
            boolean isInStock = catalogService.isItemInStock(itemId);
            Item item = catalogService.getItem(itemId);
            cart.addItem(item, isInStock);
        }
        return "/catalog/cart";
    }


}
