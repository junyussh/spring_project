package org.csu.mypetstore.domain;

import java.math.BigDecimal;
import java.util.*;

public class Cart {

    private final Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap<String, CartItem>());
    private final List<CartItem> itemList = new ArrayList<CartItem>();

    /**
     * 获取此时购物车的元素个数
     * @return
     */
    public int getNumberOfItems(){return itemList.size();}

    /**
     * 点击跳转到购物车界面时返回的购物车物品列表
     * @return
     */
    public List<CartItem> getCartItemList(){return itemList;}

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

    /**
     * 返回购物车中所有物品的总价
     * @return
     */
    public BigDecimal getSubTotal() {
        BigDecimal subTotal = new BigDecimal("0");
        Iterator<CartItem> items = getAllCartItems();
        while (items.hasNext()) {
            CartItem cartItem = (CartItem) items.next();
            Item item = cartItem.getItem();
            BigDecimal listPrice = item.getListPrice();
            BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
            subTotal = subTotal.add(listPrice.multiply(quantity));
        }
        return subTotal;
    }

    /**
     * 遍历购物车中所有物品的迭代器
     * @return
     */
    public Iterator<CartItem> getAllCartItems() {
        return itemList.iterator();
    }
}
