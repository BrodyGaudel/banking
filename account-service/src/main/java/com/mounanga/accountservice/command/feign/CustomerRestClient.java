package com.mounanga.accountservice.command.feign;

import com.mounanga.accountservice.command.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {

    /**
     * Retrieves customer details by their unique ID.
     *
     * @param id The unique identifier of the customer.
     * @return The {@code CustomerDTO} containing customer details.
     */
    @GetMapping("/bank/customers/get/{id}")
    CustomerDTO getById(@PathVariable String id);
}
