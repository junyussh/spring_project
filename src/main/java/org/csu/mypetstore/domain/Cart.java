package org.csu.mypetstore.domain;

import java.util.*;

public class Cart {

    private final Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap<String, CartItem>());
    private final List<CartItem> itemList = new ArrayList<CartItem>();

    /**
     * 在添加item时判断购物车内是否已经存在
     * @param itemId
     * @return
     */
    public boolean containsItemId(String itemId){ return itemMap.containsKey(itemId); }

    /**
     * 在添加item之后更新数量
     * @param itemId
     */
    public void incrementQuantityByItemId(String itemId){
        CartItem cartItem = (CartItem)itemMap.get(itemId);
        cartItem.incrementQuantity();
    }

    /**
     * 购物车点击移除某一行的情况，根据itemId进行移除
     * @param itemId
     * @return
     */
    public Item removeItemById(String itemId) {
        CartItem cartItem = (CartItem) itemMap.remove(itemId);
        if (cartItem == null) {
            return null;
        } else {
            itemList.remove(cartItem);
            return cartItem.getItem();
        }
    }

    /**
     * 购物车添加商品：
     * @param item
     * @param isInStock
     */
    public void addItem(Item item, boolean isInStock) {
        CartItem cartItem = (CartItem) itemMap.get(item.getItemId());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getItemId(), cartItem);
            itemList.add(cartItem);
        }
        cartItem.incrementQuantity();
    }
}
