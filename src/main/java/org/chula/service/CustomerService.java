package org.chula.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.chula.entity.Customer;
import org.chula.repository.CustomerRepository;

import java.util.List;

@ApplicationScoped
public class CustomerService {

  @Inject
  CustomerRepository customerRepository;

  public List<Customer> findAllCustomers() {
    return customerRepository.findAll().list();
  }

  @Transactional
  public void addCustomer(Customer customer) {
    customerRepository.persist(customer);
  }

}
