package com.mounanga.customerservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mounanga.customerservice.dto.CustomerPageResponseDTO;
import com.mounanga.customerservice.dto.CustomerRequestDTO;
import com.mounanga.customerservice.dto.CustomerResponseDTO;
import com.mounanga.customerservice.enums.Sex;
import com.mounanga.customerservice.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    List<CustomerResponseDTO> customers;

    @BeforeEach
    void setUp() {
        this.customers = List.of(
                new CustomerResponseDTO(UUID.randomUUID().toString(), "cin123", "john", "doe",
                        LocalDate.of(2000,9,9), "town", "world", Sex.M,
                        "user1@gmail.com", LocalDateTime.now(), null
                ),
                new CustomerResponseDTO(UUID.randomUUID().toString(), "cin456", "john", "doe",
                        LocalDate.of(2000,9,9), "town", "world", Sex.M,
                        "user2@gmail.com", LocalDateTime.now(), null
                ),
                new CustomerResponseDTO(UUID.randomUUID().toString(), "cin789", "john", "doe",
                        LocalDate.of(2000,9,9), "town", "world", Sex.M,
                        "user3@gmail.com", LocalDateTime.now(), null
                )
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerRequestDTO request = new CustomerRequestDTO("cin789", "john", "doe",
                LocalDate.of(2000,9,9), "town", "world", Sex.M, "user3@gmail.com"
        );
        CustomerResponseDTO response = new CustomerResponseDTO(UUID.randomUUID().toString(),
                "cin789", "john", "doe", LocalDate.of(2000,9,9),
                "town", "world", Sex.M, "user3@gmail.com", LocalDateTime.now(), null
        );
        Mockito.when(customerService.createCustomer(request)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        String id = customers.getFirst().id();
        CustomerRequestDTO dto = new CustomerRequestDTO("cin789", "john", "doe",
                LocalDate.of(2000,9,9), "town", "world", Sex.M, "user3@gmail.com"
        );
        CustomerResponseDTO response = customers.getFirst();
        Mockito.when(customerService.updateCustomer(Mockito.eq(id),Mockito.any())).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        String id = customers.getFirst().id();
        Mockito.when(customerService.getCustomerById(id)).thenReturn(customers.getFirst());
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/get/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(customers.getFirst())));
    }


    @Test
    void testGetAllCustomers() throws Exception {
        int page = 0;
        int size = 3;
        CustomerPageResponseDTO dto = new CustomerPageResponseDTO(1,page, size, customers);
        Mockito.when(customerService.getAllCustomers(page, size)).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/list?page="+page+"&size="+size))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void testSearchCustomers() throws Exception {
        int page = 0;
        int size = 3;
        String keyword = "john";
        CustomerPageResponseDTO dto = new CustomerPageResponseDTO(1,page, size, customers);
        Mockito.when(customerService.searchCustomers(keyword, page, size)).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/search?keyword="+keyword+"&page="+page+"&size="+size))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        String id = UUID.randomUUID().toString();
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/delete/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}