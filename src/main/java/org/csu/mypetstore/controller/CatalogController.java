package org.csu.mypetstore.controller;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;
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


    /**
     * product列表选择特定的item跳转到特定的item展示信息列表
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping("/viewItem")
    public String viewItem(String itemId,Model model){
        Item item = catalogService.getItem(itemId);
        model.addAttribute("item",item);
        return "catalog/item";
    }

    /**
     * 返回商品列表List
     * @param keyword
     * @param model
     * @return
     */
    @PostMapping("searchProducts")
    public String searchProducts(String keyword, Model model){
        if(keyword == null || keyword.length() < 1){
            String msg = "Please enter a keyword to search for, then press the search button.";
            model.addAttribute("msg",msg);
            return "common/error";
        }else {
            List<Product> productList = catalogService.searchProductList(keyword.toLowerCase());
            processProductDescription(productList);
            model.addAttribute("productList",productList);
            return "catalog/search_products";
        }
    }

    /**
     * 铁の法
        解决Thymeleaf将数据库中的Product的描述(description属性)中的<image>标签解析成普通文本的问题。
        本方法在Product中添加了imageURL属性，相当于将product的描述信息分成两部分处理了。
        同样，界面上也是用了两个标签了，一个img标签和一个lable标签。
        此方法是快速解决上述问题的临时方案，更好的方法应是更改数据库结构，将图片信息和普通文字描述信息分为两个字段存储。
     */
    private void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\"");
        product.setDescriptionImage(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
    }
    private void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }
}
