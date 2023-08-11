package com.example.rgtproject.orders.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findAllByOrderIdOrderByOrderDateDesc(String orderId);

    @Modifying
    @Query("UPDATE Orders o SET o.productName = :willChangeData WHERE o.productName = :targetData")
    void updateProductName(String targetData, String willChangeData);

     Long countOrdersByProductName(String productName);
}
