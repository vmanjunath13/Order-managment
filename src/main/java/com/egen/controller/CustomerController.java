package com.egen.controller;

import com.egen.model.dto.CustomerDto;
import com.egen.response.*;
import com.egen.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public Response<Boolean> createCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto) == Boolean.TRUE ?
                Response.<Boolean>builder().meta(ResponseMetaData.builder().statusCode(201)
                .statusMessage(StatusMessage.CREATED.name())
                .build()).data(true).build()
                :
                Response.<Boolean>builder().meta(ResponseMetaData.builder().statusCode(409)
                        .statusMessage(StatusMessage.CONFLICT.name())
                        .build()).data(false).build();
    }
}
