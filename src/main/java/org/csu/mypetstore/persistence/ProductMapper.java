package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Product;

import java.util.List;

public interface ProductMapper {

    /**
     * 用于给定categoryId查询该category的productList
     * @param categoryId
     * @return
     */
    List<Product> getProductListByCategory(String categoryId);

    /**
     * 用于给定productId返回该product
     * @param productId
     * @return
     */
    Product getProduct(String productId);

}
