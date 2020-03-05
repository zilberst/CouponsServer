package com.jesus.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jesus.coupons.beans.UserBean;
import com.jesus.coupons.beans.UserLoginDetails;
import com.jesus.coupons.data.SuccessfulLoginData;
import com.jesus.coupons.entities.User;
import com.jesus.coupons.exceptions.ApplicationException;
import com.jesus.coupons.logic.UsersController;


@RestController
@RequestMapping("/users")
public class UsersApi {

	
	@Autowired
	private UsersController usersController;

	
	@PostMapping("/login")
	public SuccessfulLoginData login(@RequestBody UserLoginDetails userLoginDetails) throws ApplicationException {
		return this.usersController.login(userLoginDetails);
	}

	
	@PostMapping
	public void createUser(@RequestBody UserBean userBean) throws ApplicationException {
		this.usersController.createUser(userBean);
	}

	
	@PutMapping
	public void updateUser(@RequestBody UserBean userBean) throws ApplicationException {
		this.usersController.updateUser(userBean);
	}

	
	@GetMapping("{userId}")
	public User getUser(@PathVariable("userId") long userId) throws ApplicationException {
		return this.usersController.getUser(userId);
	}


	@GetMapping()
	public List<User> getAllUsers() throws ApplicationException {
		return this.usersController.getAllUsers();
	}


	@DeleteMapping("{userId}")
	public void deleteUser(@PathVariable("userId") long userId) throws ApplicationException {
		this.usersController.deleteUser(userId);
	}
	
	
}