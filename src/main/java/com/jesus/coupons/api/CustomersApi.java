package com.jesus.coupons.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jesus.coupons.data.UserLoginData;
import com.jesus.coupons.entities.Customer;
import com.jesus.coupons.exceptions.ApplicationException;
import com.jesus.coupons.logic.CustomersController;


@RestController
@RequestMapping("/customers")
public class CustomersApi {

	
	@Autowired
	private CustomersController customersController;

	
	@PostMapping
	public void createCustomer(@RequestBody Customer customer) throws ApplicationException {
		this.customersController.createCustomer(customer);
	}

	
	@PutMapping
	public void updateCustomer(@RequestBody Customer customer) throws ApplicationException {
		this.customersController.updateCustomer(customer);
	}

	
	@GetMapping("{customerId}")
	public Customer getCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
		return this.customersController.getCustomer(customerId);
	}
	
	@GetMapping("/getMyDetails")
	public Customer getMyDetails(HttpServletRequest request) throws ApplicationException {
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		return this.customersController.getCustomer(userData.getId());
	}

	@GetMapping()
	public List<Customer> getAllCustomers() throws ApplicationException {
		return this.customersController.getAllCustomers();
	}


	@DeleteMapping("{customerId}")
	public void deleteCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
		this.customersController.deleteCustomer(customerId);
	}

	
}