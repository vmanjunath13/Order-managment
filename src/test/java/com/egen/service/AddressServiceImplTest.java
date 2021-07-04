package com.egen.service;

import com.egen.exception.AddressServiceException;
import com.egen.model.entity.Address;
import com.egen.repository.AddressRepository;
import com.egen.service.implementation.AddressServiceImplementation;
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

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceImplTest {
    @Bean
    public AddressService getService() {
        return new AddressServiceImplementation();
    }

    @Autowired
    AddressService addressService;

    @MockBean
    AddressRepository addressRepository;
    Address address;
    @Before
    public void setUpAddress() {
        address = new Address();
        address.setAddressId(UUID.randomUUID().toString());
        address.setAddressLine1("2901 S King Dr");
        address.setAddressLine2("1301");
        address.setCity("Chicago");
        address.setState("IL");
        address.setPostalCode("60616");

        Mockito.when(addressRepository.save(address)).thenReturn(address);
    }

    @After
    public void cleanUpAddress() {
        System.out.println("Address Service testing completed");
    }

    @Test
    public void createAddress() {
        Address new_address = addressService.createAddress(address);
        Assert.assertEquals("New address not created", address, new_address);
    }

    @Test(expected = AddressServiceException.class)
    public void createAddressFailed() {
        addressService.createAddress(null);
    }
}
