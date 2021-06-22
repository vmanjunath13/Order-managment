package com.egen.repository;

import com.egen.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByFirstNameAndLastName(String firstName, String lastName);
}
