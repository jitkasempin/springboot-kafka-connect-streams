package com.mycompany.storeapi.service;

import com.mycompany.storeapi.exception.CustomerDeletionException;
import com.mycompany.storeapi.exception.CustomerNotFoundException;
import com.mycompany.storeapi.model.Customer;
import com.mycompany.storeapi.repository.CustomerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        try {
            customerRepository.delete(customer);
        } catch (DataIntegrityViolationException e) {
            throw new CustomerDeletionException(String.format("Customer with id '%s' cannot be deleted", customer.getId()));
        }
    }

    @Override
    public Customer validateAndGetCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id '%s' not found", id)));
    }
}
