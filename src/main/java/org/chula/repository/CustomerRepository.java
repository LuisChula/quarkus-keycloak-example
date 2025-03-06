package org.chula.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.chula.entity.Customer;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {
}
