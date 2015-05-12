package org.chaminda.customer.service;

import java.util.List;

import org.chaminda.customer.entity.Customer;


public interface CustomerService {
	
	Customer saveOrUpdateCustomer(Customer customer);
	
	List<Customer> findAllCustomers();
	
	Customer findByCustomerId(Long customerId);
	
	void deleteCustomer(Long customerId);

}
