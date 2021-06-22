package com.egen.service;

import com.egen.model.dto.CustomerDto;

public interface CustomerService {
    boolean findCustomerByEmail(String email);
    boolean findCustomerByFirstNameAndLastName(String firstName, String lastName);
    boolean createCustomer(CustomerDto customerDto);
}
