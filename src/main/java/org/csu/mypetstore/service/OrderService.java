package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.persistence.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 给定username返回该username的历史订单列表
     * @param username
     * @return
     */
    public List<Order> getOrderListByUsername(String username){return orderMapper.getOrderListByUsername(username);}


}
