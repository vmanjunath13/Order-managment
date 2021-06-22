package com.egen.repository;

import com.egen.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findAllByDateOrderedBetween(Timestamp startTime, Timestamp endTime);

    @Query(nativeQuery = true, value = "SELECT * FROM Order ord, Address adr " +
            "WHERE ord.shippingAddress = adr.addressId " +
            "AND adr.postalCode =:zipcode ORDER BY ord.orderTotal desc limit 10")
    List<Order> findAllByShippingAddressZipcode(String zipcode);

    Page<Order> findAll(Pageable pageable);
}
