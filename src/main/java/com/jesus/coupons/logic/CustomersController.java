package com.jesus.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jesus.coupons.dao.ICustomersDao;
import com.jesus.coupons.entities.Customer;
import com.jesus.coupons.enums.ErrorTypes;
import com.jesus.coupons.enums.UserTypes;
import com.jesus.coupons.exceptions.ApplicationException;


@Controller
public class CustomersController {

	@Autowired
	private ICustomersDao customersDao;

	@Autowired
	private UsersController usersController;


	public CustomersController() {
	}


	public void createCustomer(Customer customer) throws ApplicationException {
		customer.getUser().setUserType(UserTypes.CUSTOMER);
		customerValidations(customer);
		usersController.userValidations(customer.getUser());
		usersController.passwordValidations(customer.getUser().getPassword());
		
		//Hashing the password and replacing it with the current one
		customer.getUser().setPassword(String.valueOf(customer.getUser().getPassword().hashCode()));

		try {
			this.customersDao.save(customer);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to create customer");
		}
	}


	public void updateCustomer(Customer customer) throws ApplicationException {
		customer.getUser().setUserType(UserTypes.CUSTOMER);
		customerValidations(customer);
		usersController.userValidations(customer.getUser());

		//Hashing the password and replacing it with the current one
		customer.getUser().setPassword(String.valueOf(customer.getUser().getPassword().hashCode()));
		try {
			this.customersDao.save(customer);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to update customer");
		}
	}


	private void customerValidations(Customer customer) throws ApplicationException {
		if (customer.getAmountOfKids() < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_AMOUNT_OF_KIDS, "Invalid amount of kids.");
		}
	}


	public void deleteCustomer(long customerId) throws ApplicationException {
		try {
			this.customersDao.deleteById(customerId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete customer");
		}
	}


	public Customer getCustomer(long customerId) throws ApplicationException {
		try {
			return this.customersDao.findById(customerId).get();
		} catch (Exception e) {
			if (e.getCause() == null) {
				throw new ApplicationException(ErrorTypes.INVALID_CUSTOMER, "Customer not found");
			}
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get customer");
		}
		
	}


	public List<Customer> getAllCustomers() throws ApplicationException {
		List<Customer> customers;
		try {
			customers = (List<Customer>) this.customersDao.findAll();
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get customers");
		}

		if (customers.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_CUSTOMER, "Customers not found");
		}
		return customers;
	}


	public boolean isCustomerExistsById(long id) throws ApplicationException {
		try {
			return this.customersDao.existsById(id);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to check customer id");
		}
	}


}