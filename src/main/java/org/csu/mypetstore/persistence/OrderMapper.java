package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {

    List<Order> getOrderListByUsername(String username);

    void insertOrder(Order order);

    Order getOrder(int orderId);

    void insertOrderStatus(Order order);
}
