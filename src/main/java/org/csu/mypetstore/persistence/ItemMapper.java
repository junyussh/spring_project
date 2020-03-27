package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemMapper {

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);

    int getInventoryQuantity(String itemId);

    void updateInventoryQuantity(Map<String, Object> param);

}
