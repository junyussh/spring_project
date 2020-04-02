package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {
    /**
     * 商品模块
     */

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ItemMapper itemMapper;

    public Category getCategory(String categoryId) { return categoryMapper.getCategory(categoryId); }

    /**
     * 用于给定productId返回该product
     * @param productId
     * @return
     */
    public Product getProduct(String productId) { return productMapper.getProduct(productId); }

    /**
     * 用于给定categoryId查询该category的productList
     * @param categoryId
     * @return
     */
    public List<Product> getProductListByCategory(String categoryId) { return productMapper.getProductListByCategory(categoryId); }

    /**
     * 根据productId查询对应的item的列表
     * @param productId
     * @return
     */
    public List<Item> getItemListByProduct(String productId){return itemMapper.getItemListByProduct(productId);}

    /**
     * 根据itemId得到对应的item
     * @param itemId
     * @return
     */
    public Item getItem(String itemId){return itemMapper.getItem(itemId);}

    public boolean isItemInStock(String itemId) {
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }

    /**搜索商品*/
    public List<Product> searchProductList(String keyword) {
        return productMapper.searchProductList("%" + keyword.toLowerCase() + "%");
    }
}
