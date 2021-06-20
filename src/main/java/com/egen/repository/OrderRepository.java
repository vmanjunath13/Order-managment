package com.egen.repository;

import com.egen.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, String> {

    @Query("SELECT ord FROM Order ord WHERE ord.customer_id = ?1 AND ord.orderId = ?2")
    Optional<Order> findByCustomerId(String customer_id, String id);

    @Query("SELECT ord FROM Order ord " +
    "WHERE ord.dateCreated BETWEEN :startTime AND :endTime")
    List<Order> findByStartDateBetween(Timestamp startTime, Timestamp endTime);
}
