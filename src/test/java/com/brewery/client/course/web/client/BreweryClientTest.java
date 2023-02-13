package com.brewery.client.course.web.client;

import com.brewery.client.course.web.model.BeerDto;
import com.brewery.client.course.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void getBeerById() {
        BeerDto dto = client.getBeerById(UUID.randomUUID());

        assertNotNull(dto);
    }

    @Test
    void saveNewBeer() {
        URI uri = client.saveNewBeer(BeerDto.builder().build());

        assertNotNull(uri);
    }

    @Test
    void updateBeer() {
        client.updateBeer(UUID.randomUUID(), BeerDto.builder().build());
    }

    @Test
    void deleteBeer() {
        client.deleteBeer(UUID.randomUUID());
    }

    @Test
    void getCustomerById() {
        CustomerDto dto = client.getCustomerById(UUID.randomUUID());

        assertNotNull(dto);
    }

    @Test
    void saveNewCustomer() {
        URI uri = client.saveNewCustomer(CustomerDto.builder().build());

        assertNotNull(uri);
    }

    @Test
    void updateCustomer() {
        client.updateCustomer(UUID.randomUUID(), CustomerDto.builder().build());
    }

    @Test
    void deleteCustomer() {
        client.deleteCustomer(UUID.randomUUID());
    }

}