package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.LineItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.Sequence;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.LineItemMapper;
import org.csu.mypetstore.persistence.OrderMapper;
import org.csu.mypetstore.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private LineItemMapper lineItemMapper;

    /**
     * 给定username返回该username的历史订单列表
     * @param username
     * @return
     */
    public List<Order> getOrderListByUsername(String username){return orderMapper.getOrderListByUsername(username);}

    /**
     * 用于获取订单序列号
     * @param name
     * @return
     */
    public int getNextId(String name){
        Sequence sequence = new Sequence(name,-1);
        sequence = sequenceMapper.getSequence(sequence);
        if (sequence == null){
            throw new RuntimeException("Error: A null sequence was returned from the database (could not get next " + name
                    + " sequence).");
        }
        Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
        sequenceMapper.updateSequence(parameterObject);
        return sequence.getNextId();
    }

    /**
     * 提交订单，跳转到确认订单界面
     * @param order
     */
    @Transactional
    public void insertOrder(Order order) {
        order.setOrderId(getNextId("ordernum"));

        // TODO 更改库存数量
        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            String itemId = lineItem.getItemId();
            Integer increment = new Integer(lineItem.getQuantity());
            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("itemId", itemId);
            param.put("increment", increment);
            itemMapper.updateInventoryQuantity(param);
        }

        // 更改order中添加上去的和lineitem中添加上去的

        System.out.println(order.getBillAddress1());
        orderMapper.insertOrder(order);
        orderMapper.insertOrderStatus(order);
        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            lineItem.setOrderId(order.getOrderId());
            lineItemMapper.insertLineItem(lineItem);
        }
    }

    /**
     * 渲染订单完成界面返回的orderlist
     * @param orderId
     * @return
     */
    @Transactional
    public Order getOrder(int orderId){

        // TODO 由于订单第一个界面订单完成的提交仍有bug=>订单确认界面无法正常显示=>最后完成交易的订单查看界面就查不到该订单

        Order order = orderMapper.getOrder(orderId);
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));


        return order;
    }

}
