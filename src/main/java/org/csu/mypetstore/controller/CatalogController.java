package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;



    /**
     * 跳转到主界面
     * @return
     */
    @GetMapping("/main")
    public String view(){
        return "catalog/main";
    }


    /**
     * 主界面跳转到特定Category中显示product列表的界面
     * @param categoryId
     * @param model
     * @return
     */
    @GetMapping("/viewCategory")
    public String viewCategory(String categoryId, Model model){
        if (categoryId != null){
            Category category = catalogService.getCategory(categoryId);
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category",category);
            model.addAttribute("productList",productList);
            return "catalog/category";
        }
        return "catalog/main";
    }

    /**
     * Category列表选择特定的product跳转到特定的item列表界面
     * @param productId
     * @param model
     * @return
     */
    @GetMapping("/viewProduct")
    public String viewProduct(String productId,Model model){
        if (productId != null){
            Product product = catalogService.getProduct(productId);
            List<Item> itemList = catalogService.getItemListByProduct(productId);
            model.addAttribute("product",product);
            model.addAttribute("itemList",itemList);
            return "catalog/product";
        }
        return "catalog/main";
    }


    @GetMapping("/viewItem")
    public String viewItem(String itemId,Model model){
        Item item = catalogService.getItem(itemId);
        model.addAttribute("item",item);
        return "catalog/item";
    }
}
