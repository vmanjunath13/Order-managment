package com.egen.service.implementation;

import com.egen.exception.AddressServiceException;
import com.egen.exception.ResourceNotFoundException;
import com.egen.model.entity.Address;
import com.egen.repository.AddressRepository;
import com.egen.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AddressServiceImplementation implements AddressService {

    AddressRepository addressRepository;

    public AddressServiceImplementation(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressServiceImplementation() {

    }

    @Transactional(readOnly = true)
    public boolean findAddressByAddressLine1(String addressLine1) {
        try {
            return addressRepository.findAddressByAddressLine1(addressLine1);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Address is not found for the given address line 1: " + addressLine1);
        }
    }

    @Transactional(readOnly = true)
    public boolean findAddressByCityAndAddressLine1(String city, String addressLine1) {
        try {
            return addressRepository.findAddressByCityAndAddressLine1(city, addressLine1);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Address is not found for the given city and address line 1: " + city + " " + addressLine1);
        }
    }

    @Transactional
    public Address createAddress(Address address) {
        return Optional.ofNullable(addressRepository.save(address)).orElseThrow(
                () -> new AddressServiceException("Failed to add address to system! Please try again")
        );
    }
}
