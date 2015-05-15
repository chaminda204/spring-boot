package org.chaminda.customer.controller;

import java.util.List;

import org.chaminda.customer.entity.Customer;
import org.chaminda.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
	Customer add(@RequestBody Customer input) {

		return customerService.saveOrUpdateCustomer(input);

	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	List<Customer> findAllCustomers() {
		return customerService.findAllCustomers();
	}

	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	Customer findById(@PathVariable Long customerId) {
		return customerService.findByCustomerId(customerId);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{/{customerId}")
	public void delete(@PathVariable Long customerId) {
		customerService.deleteCustomer(customerId);
	}

}
