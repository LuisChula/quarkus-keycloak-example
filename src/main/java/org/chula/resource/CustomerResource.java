package org.chula.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.chula.entity.Customer;
import org.chula.service.CustomerService;

import java.util.List;

@Path("/api/v2/customer")
public class CustomerResource {

  @Inject
  CustomerService customerService;

  @GET
  @RolesAllowed("manager")
  public List<Customer> findAllCustomers() {
    return customerService.findAllCustomers();
  }

  @POST
  @RolesAllowed("manager")
  public void addCustomer(Customer customer) {
    customerService.addCustomer(customer);
  }

}
