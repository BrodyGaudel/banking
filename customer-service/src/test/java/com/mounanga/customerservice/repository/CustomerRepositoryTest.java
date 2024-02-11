package com.mounanga.customerservice.repository;

import com.mounanga.customerservice.entity.Customer;
import com.mounanga.customerservice.enums.Sex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.save(
                Customer.builder().sex(Sex.M).cin("123cin321").name("john").firstname("doe")
                        .email("john.doe@mail.com").placeOfBirth("world").nationality("world")
                        .dateOfBirth(LocalDate.now()).creation(LocalDateTime.now())
                        .build()
        );
        customerRepository.save(
                Customer.builder().sex(Sex.M).cin("213cin312").name("marvin").firstname("doe")
                        .email("marvin@mail.com").placeOfBirth("world").nationality("world")
                        .dateOfBirth(LocalDate.now()).creation(LocalDateTime.now())
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void testCinExistsShouldReturnTrue() {
        String cin = "213cin312";
        boolean checked = customerRepository.cinExists(cin);
        assertTrue(checked);
    }

    @Test
    void testCinExistsShouldReturnFalse() {
        String cin = "777cin222";
        boolean checked = customerRepository.cinExists(cin);
        assertFalse(checked);
    }

    @Test
    void testEmailExistsShouldReturnTrue() {
        String email = "marvin@mail.com";
        boolean checked = customerRepository.emailExists(email);
        assertTrue(checked);
    }

    @Test
    void testEmailExistsShouldReturnFalse() {
        String email = "java@mail.com";
        boolean checked = customerRepository.emailExists(email);
        assertFalse(checked);
    }

    @Test
    void testSearchShouldFound() {
        String keyword = "john";
        int page = 0;
        int size = 2;
        Page<Customer> customerPage = customerRepository.search(
                "%"+keyword+"%", PageRequest.of(page, size)
        );
        assertNotNull(customerPage);
        List<Customer> customers = customerPage.getContent();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
        assertEquals(keyword, customers.getFirst().getName());
    }

    @Test
    void testSearchShouldNotFound() {
        String keyword = "brody";
        int page = 0;
        int size = 2;
        Page<Customer> customerPage = customerRepository.search(
                "%"+keyword+"%", PageRequest.of(page, size)
        );
        assertNotNull(customerPage);
        List<Customer> customers = customerPage.getContent();
        assertNotNull(customers);
        assertTrue(customers.isEmpty());
    }
}