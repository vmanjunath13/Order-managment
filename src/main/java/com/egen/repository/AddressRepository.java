package com.egen.repository;

import com.egen.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    boolean findAddressByAddressLine1(String addressLine1);
    boolean findAddressByCityAndAddressLine1(String city, String addressLine1);
}
