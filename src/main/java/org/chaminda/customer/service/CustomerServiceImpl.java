package org.chaminda.customer.service;

import java.util.List;

import org.chaminda.customer.dao.CustomerRepository;
import org.chaminda.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository repository;

	@Override
	public Customer saveOrUpdateCustomer(Customer customer) {

		return repository.save(customer);
	}

	@Override
	public List<Customer> findAllCustomers() {
		return repository.findAll();
	}

	@Override
	public Customer findByCustomerId(Long customerId) {
		return repository.findOne(customerId);
	}

	@Override
	public void deleteCustomer(Long customerId) {
		repository.delete(customerId);

	}

}
