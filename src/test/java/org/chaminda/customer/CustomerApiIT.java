package org.chaminda.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.chaminda.customer.entity.Customer;
import org.junit.Test;

public class CustomerApiIT extends BaseIntegrationTest<Customer> {

	@Test
	public void shouldCreateNewCustomer() {
		// given
		Customer customer = getNewCustomer();

		// when
		Customer response = postEntity("/customer/add", Customer.class, customer);

		// then
		assertNotNull(response);
		assertNotNull(response.getId());
		assertEquals(customer, response);

	}

	@Test
	public void shouldRetrieveCustomerForGivenId() {
		// given
		Customer customer = getNewCustomer();
		Customer savedCustomer = postEntity("/customer/add", Customer.class, customer);
		Map<String, String> vars = Collections.singletonMap("customerId", savedCustomer.getId() + "");

		// when
		Customer response = getEntity("/customer/{customerId}", Customer.class, vars);

		// then
		assertNotNull(response);
		assertEquals(customer, response);

	}

	@Test
	public void shouldGetAllCustomers() {
		// given
		Customer customer = getNewCustomer();
		postEntity("/customer/add", Customer.class, customer);
		// when
		Collection<Customer> response = getList("/customer/all");

		// then
		assertNotNull(response);
		//assertTrue(response.contains(customer));
	}

	private Customer getNewCustomer() {
		Customer customer = new Customer();
		customer.setFirstName("Chaminda");
		customer.setLastName("Ari");
		customer.setPhone("0111000234");
		return customer;
	}
}
