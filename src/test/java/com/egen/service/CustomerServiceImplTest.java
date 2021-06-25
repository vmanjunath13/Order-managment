package com.egen.service;


import com.egen.exception.CustomerServiceException;
import com.egen.model.dto.CustomerDto;
import com.egen.model.entity.Customer;
import com.egen.repository.CustomerRepository;
import com.egen.service.implementation.CustomerServiceImplementation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Bean
    public CustomerService getService() {
        return new CustomerServiceImplementation();
    }

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;
    Customer customer;
    CustomerDto customerDto;
    @Before
    public void setUp(){
        customer  = new Customer();
        customerDto = new CustomerDto();
        customer.setCustomer_id(UUID.randomUUID().toString());
        customer.setEmail("vm@gmail.com");
        customer.setFirstName("Vaishnavi");
        customer.setLastName("Manjunath");


        //Setting DTO values to entity to
        customerDto.setCustomer_id(UUID.randomUUID().toString());
        customerDto.setEmail("vm@gmail.com");
        customerDto.setFirstName("Vaishnavi");
        customerDto.setLastName("Manjunath");
        customer.setCustomer_id(customerDto.getCustomer_id());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());

        Mockito.when(customerRepository.findById(customer.getCustomer_id())).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
    }
    @After
    public void cleanUpCustomer() {
        System.out.println("Customer Service testing completed");
    }

    @Test
    public void findCustomer() {
        boolean isExisting = customerService.findCustomerByEmail(customer.getEmail());
        Assert.assertTrue("Customer doesnt exist", isExisting);
    }

    @Test(expected = CustomerServiceException.class)
    public void customerNotFound() {
        customerService.findCustomerByEmail(customer.getEmail());
    }

    @Test
    public void createCustomer() {
        boolean new_customer = customerService.createCustomer(customerDto);
        Assert.assertTrue("Failed to create customer", new_customer);
    }
    @Test(expected = CustomerServiceException.class)
    public void createCustomerFailed() {
        customerService.createCustomer(customerDto);
    }


}
