package com.egen.service;

import com.egen.model.entity.Address;

public interface AddressService {

    boolean findAddressByAddressLine1(String addressLine1);
    boolean findAddressByCityAndAddressLine1(String city, String addressLine1);
    Address createAddress(Address address);
}
