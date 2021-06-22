package com.egen.service.implementation;

import com.egen.exception.CustomerServiceException;
import com.egen.exception.ResourceNotFoundException;
import com.egen.model.dto.CustomerDto;
import com.egen.model.entity.Customer;
import com.egen.repository.CustomerRepository;
import com.egen.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {

    final CustomerRepository customerRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public boolean findCustomerByEmail(String email) {
        try {
            return customerRepository.findCustomerByEmail(email).isPresent();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Customer is not found for the given email: " + email);
        }
    }

    @Transactional(readOnly = true)
    public boolean findCustomerByFirstNameAndLastName(String firstName, String lastName) {
        try {
            return customerRepository.findCustomerByFirstNameAndLastName(firstName, lastName).isPresent();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Customer is not found for the given name: " + firstName + " " + lastName);
        }
    }

    @Transactional
    public boolean createCustomer(CustomerDto customerDto) {
        try {
            Optional<Customer> customer = customerRepository.findCustomerByEmail(customerDto.getEmail());
            if (customer.isPresent())
                throw new CustomerServiceException("Customer already present");

            customerRepository.save(new Customer(customerDto.getFirstName(), customerDto.getLastName(), customerDto.getEmail()));
            return true;
        } catch (Exception e) {
            throw new CustomerServiceException("The email " + customerDto.getEmail() + " is already registered in our system. Trying logging in!");
        }
    }
}
