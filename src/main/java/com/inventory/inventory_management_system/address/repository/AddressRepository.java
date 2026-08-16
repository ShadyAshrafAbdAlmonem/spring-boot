package com.inventory.inventory_management_system.address.repository;

import com.inventory.inventory_management_system.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // Find addresses by city
    List<Address> findByCity(String city);

    // Find addresses by state
    List<Address> findByState(String state);

    // Find addresses by country
    List<Address> findByCountry(String country);

    // Find addresses by zip code
    List<Address> findByZipCode(String zipCode);

    // Find addresses by city and state
    List<Address> findByCityAndState(String city, String state);

    // Find addresses by city and country
    List<Address> findByCityAndCountry(String city, String country);

    // Find addresses by state and country
    List<Address> findByStateAndCountry(String state, String country);

    // Find addresses by street (case-insensitive, partial match)
    List<Address> findByStreetContainingIgnoreCase(String street);

    // Find addresses by country and city (case-insensitive)
    List<Address> findByCountryAndCityContainingIgnoreCase(String country, String city);
}
