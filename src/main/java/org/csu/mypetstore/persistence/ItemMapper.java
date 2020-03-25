package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMapper {

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);
}
