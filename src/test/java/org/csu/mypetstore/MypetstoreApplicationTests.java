package org.csu.mypetstore;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("org.csu.mypetstore.persistence")
class MypetstoreApplicationTests {

    @Autowired
    public CatalogService catalogService;

    @Autowired
    public AccountService accountService;

    @Autowired
    public OrderService orderService;


    @Test
    void contextLoads() {
    }

    @Test
    void testCategory(){
        Category category = catalogService.getCategory("BIRDS");
        System.out.println(category);
    }

    @Test
    void testProduct(){
        List<Product> productList = catalogService.getProductListByCategory("BIRDS");
        System.out.println(productList.size());
    }

    @Test
    void testItem(){
        List<Item> itemList = catalogService.getItemListByProduct("FI-FW-01");
        System.out.println("Itemlist size = "+itemList.size());
        Product product = catalogService.getProduct("FI-FW-01");
        System.out.println("product info "+product.getName());
    }

    @Test
    void testAccount(){
        System.out.println("account name is "+accountService.getAccount("a","a").getUsername());
    }

    @Test
    void testOrderList(){
        System.out.println("orderlist size is "+orderService.getOrderListByUsername("a").size());
    }
}
