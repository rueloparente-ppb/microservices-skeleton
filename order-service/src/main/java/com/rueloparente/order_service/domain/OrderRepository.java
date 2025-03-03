package com.rueloparente.order_service.domain;

import com.rueloparente.order_service.dto.OrderSummary;
import com.rueloparente.order_service.value_objects.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface OrderRepository extends JpaRepository<Order, Long> {

    default void updateOrderStatus(String orderNumber, OrderStatus status) {
        Order order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(status);
        this.save(order);
    }

    Order save(Order order);

    List<Order> findByStatus(OrderStatus status);

    Optional<Order> findByOrderNumber(String orderNumber);

    @Query(
            """
      select new com.rueloparente.order_service.dto.OrderSummary(o.orderNumber, o.status)
      from Order o
      where o.userName = :userName
      """)
    List<OrderSummary> findByUserName(String userName);

    @Query(
            """
        select distinct o
        from Order o left join fetch o.items
        where o.userName = :userName and o.orderNumber = :orderNumber
      """)
    Optional<Order> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
