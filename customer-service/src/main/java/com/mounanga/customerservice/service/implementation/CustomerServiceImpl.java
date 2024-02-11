package com.mounanga.customerservice.service.implementation;

import com.mounanga.customerservice.dto.CustomerPageResponseDTO;
import com.mounanga.customerservice.dto.CustomerRequestDTO;
import com.mounanga.customerservice.dto.CustomerResponseDTO;
import com.mounanga.customerservice.entity.Customer;
import com.mounanga.customerservice.exception.CinAlreadyExistException;
import com.mounanga.customerservice.exception.CustomerNotAdultException;
import com.mounanga.customerservice.exception.CustomerNotFoundException;
import com.mounanga.customerservice.exception.EmailAlreadyExistException;
import com.mounanga.customerservice.repository.CustomerRepository;
import com.mounanga.customerservice.service.CustomerService;
import com.mounanga.customerservice.util.Mappers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final Mappers mappers;

    public CustomerServiceImpl(CustomerRepository customerRepository, Mappers mappers) {
        this.customerRepository = customerRepository;
        this.mappers = mappers;
    }


    /**
     * Creates a new customer based on the provided CustomerRequestDTO.
     *
     * @param dto the CustomerRequestDTO containing information about the customer to be created.
     * @return a CustomerResponseDTO representing the newly created customer.
     * @throws CinAlreadyExistException   if the provided CustomerRequestDTO contains a CIN (Customer Identification Number) that already exists.
     * @throws EmailAlreadyExistException if the provided CustomerRequestDTO contains an email address that already exists.
     * @throws CustomerNotAdultException  if the provided CustomerRequestDTO indicates that the customer is not an adult.
     */
    @Override
    public CustomerResponseDTO createCustomer(@NotNull CustomerRequestDTO dto) throws CinAlreadyExistException, EmailAlreadyExistException, CustomerNotAdultException {
        log.info("In createCustomer() : "+ dto);
        validateDataBeforeSave(dto);
        Customer customer = mappers.fromCustomerRequestDTO(dto);
        customer.setLastUpdated(null);
        customer.setCreation(LocalDateTime.now());
        Customer customerSaved = customerRepository.save(customer);
        log.info("customer created successfully with id : "+customerSaved.getId());
        return mappers.fromCustomer(customer);
    }

    /**
     * Updates an existing customer with the provided ID, using the information from the CustomerRequestDTO.
     *
     * @param id  the ID of the customer to be updated.
     * @param dto the CustomerRequestDTO containing updated information about the customer.
     * @return a CustomerResponseDTO representing the updated customer.
     * @throws CustomerNotFoundException  if no customer with the provided ID is found.
     * @throws CinAlreadyExistException   if the provided CustomerRequestDTO contains a CIN (Customer Identification Number) that already exists.
     * @throws EmailAlreadyExistException if the provided CustomerRequestDTO contains an email address that already exists.
     * @throws CustomerNotAdultException  if the provided CustomerRequestDTO indicates that the customer is not an adult.
     */
    @Override
    public CustomerResponseDTO updateCustomer(String id, @NotNull CustomerRequestDTO dto) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, CustomerNotAdultException {
        log.info("In updateCustomer() : "+"with id :"+id+ "and data :"+dto);
        Customer customer = customerRepository.findById(id).orElseThrow( () -> new CustomerNotFoundException("customer with id '"+id+"' not found"));
        validateDataBeforeUpdate(customer, dto);
        updateCustomerWithNewData(customer, dto);
        Customer customerUpdated = customerRepository.save(customer);
        log.info("customer updated at :"+customerUpdated.getLastUpdated());
        return mappers.fromCustomer(customerUpdated);
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The response DTO containing the details of the retrieved customer.
     * @throws CustomerNotFoundException if the customer with the given ID is not found.
     */
    @Override
    public CustomerResponseDTO getCustomerById(String id) throws CustomerNotFoundException {
        log.info("In getCustomerById() : with id = "+id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        log.info("customer found");
        return mappers.fromCustomer(customer);
    }

    /**
     * Retrieves all customers with pagination.
     *
     * @param page The page number.
     * @param size The size of each page.
     * @return The response DTO containing a page of customers.
     */
    @Override
    public CustomerPageResponseDTO getAllCustomers(int page, int size) {
        log.info("In getAllCustomers() with page="+page+". and size="+size);
        Page<Customer> customerPage = customerRepository.findAll(PageRequest.of(page, size));
        log.info("customer(s) found");
        return new CustomerPageResponseDTO(customerPage.getTotalPages(), page, size,
                mappers.fromListOfCustomers(customerPage.getContent())
        );
    }

    /**
     * Searches for customers based on a keyword with pagination.
     *
     * @param keyword The keyword to search for.
     * @param page    The page number.
     * @param size    The size of each page.
     * @return The response DTO containing a page of searched customers.
     */
    @Override
    public CustomerPageResponseDTO searchCustomers(String keyword, int page, int size) {
        log.info("In searchCustomers() with keyword = '"+keyword+"' and page = '"+page+"' and size = "+size);
        Page<Customer> customerPage = customerRepository.search("%"+keyword+"%", PageRequest.of(page, size));
        log.info("customer(s) found");
        return new CustomerPageResponseDTO(customerPage.getTotalPages(), page, size,
                mappers.fromListOfCustomers(customerPage.getContent())
        );
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id The ID of the customer to delete.
     */
    @Override
    public void deleteCustomerById(String id) {
        log.info("In deleteCustomerById() : with id = "+id);
        customerRepository.deleteById(id);
    }



    /**
     * Checks if a given date corresponds to an adult (18 years or older).
     *
     * @param date The birthdate to be checked.
     * @return {@code true} if the person is an adult, {@code false} otherwise.
     */
    private boolean isAdult(LocalDate date){
        LocalDate now = LocalDate.now();
        Period age = Period.between(date, now);
        return age.getYears() <= 18 && (age.getYears() != 18 || (age.getMonths() <= 0 && age.getDays() <= 0));
    }

    /**
     * Updates the given customer with the data from the provided CustomerRequestDTO.
     *
     * @param customer The customer to be updated.
     * @param dto      The CustomerRequestDTO containing the new data.
     */
    private void updateCustomerWithNewData(@NotNull Customer customer, @NotNull CustomerRequestDTO dto){
        customer.setCin(dto.cin());
        customer.setEmail(dto.email());
        customer.setFirstname(dto.firstname());
        customer.setName(dto.name());
        customer.setDateOfBirth(dto.dateOfBirth());
        customer.setPlaceOfBirth(dto.placeOfBirth());
        customer.setNationality(dto.nationality());
        customer.setSex(dto.sex());
        customer.setLastUpdated(LocalDateTime.now());
    }

    /**
     * Validates the provided customer data against existing records and business rules.
     * Throws exceptions if validation fails.
     *
     * @param customer           The customer object to be validated.
     * @param dto                The CustomerRequestDTO containing the new data.
     * @throws CustomerNotAdultException    If the customer is not an adult.
     * @throws EmailAlreadyExistException   If there is already a customer with the new email.
     * @throws CinAlreadyExistException     If there is already a customer with the new cin.
     */
    private void validateDataBeforeUpdate(@NotNull Customer customer, @NotNull CustomerRequestDTO dto) throws CustomerNotAdultException, EmailAlreadyExistException, CinAlreadyExistException {
        if (isAdult(dto.dateOfBirth())) {
            throw new CustomerNotAdultException("Customer is not an adult");
        }

        if (!customer.getEmail().equals(dto.email()) && customerRepository.emailExists(dto.email())) {
            throw new EmailAlreadyExistException("There is already a customer with this new email");
        }

        if (!customer.getCin().equals(dto.cin()) && customerRepository.cinExists(dto.cin())) {
            throw new CinAlreadyExistException("There is already a customer with this new cin");
        }
    }

    /**
     * Validates the provided customer data against existing records and business rules.
     * Throws exceptions if validation fails.
     *
     * @param dto                The CustomerRequestDTO containing the new data.
     * @throws CustomerNotAdultException    If the customer is not an adult.
     * @throws CinAlreadyExistException     If there is already a customer with the new cin.
     * @throws EmailAlreadyExistException   If there is already a customer with the new email.
     */
    private void validateDataBeforeSave(@NotNull CustomerRequestDTO dto) throws CustomerNotAdultException, CinAlreadyExistException, EmailAlreadyExistException {
        if (isAdult(dto.dateOfBirth())) {
            throw new CustomerNotAdultException("Customer is not an adult");
        }

        if (customerRepository.cinExists(dto.cin())) {
            throw new CinAlreadyExistException("There is already a customer with this cin");
        }

        if (customerRepository.emailExists(dto.email())) {
            throw new EmailAlreadyExistException("There is already a customer with this email");
        }
    }


}
