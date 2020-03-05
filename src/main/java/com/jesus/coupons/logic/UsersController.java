package com.jesus.coupons.logic;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jesus.coupons.beans.UserBean;
import com.jesus.coupons.beans.UserLoginDetails;
import com.jesus.coupons.dao.IUsersDao;
import com.jesus.coupons.data.SuccessfulLoginData;
import com.jesus.coupons.data.UserLoginData;
import com.jesus.coupons.entities.User;
import com.jesus.coupons.enums.ErrorTypes;
import com.jesus.coupons.enums.UserTypes;
import com.jesus.coupons.exceptions.ApplicationException;


@Controller
public class UsersController {

	
	@Autowired
	private IUsersDao usersDao;

	@Autowired
	private CompaniesController companiesController;

	@Autowired
	private CustomersController customersController;

	@Autowired
	private CacheController cacheController;

	private static final String ENCRYPTION_TOKEN_SALT = "AASDFHSJFHJHKAAAAA-3423@#$@#$";


	public UsersController() {
	}


	public void createUser(UserBean userBean) throws ApplicationException {
		
		User user = createUserFromUserBean(userBean);

		userValidations(user);
		
		passwordValidations(user.getPassword());
		
		//Hashing the password and replacing it with the current one
		user.setPassword(String.valueOf(user.getPassword().hashCode()));
		try {
			this.usersDao.save(user);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to create user");
		}
	}


	public void updateUser(UserBean userBean) throws ApplicationException {
		
		User user = createUserFromUserBean(userBean);
		
		userValidations(user);
		
		//Hashing the password and replacing it with the current one
		user.setPassword(String.valueOf(user.getPassword().hashCode()));
		try {
			this.usersDao.save(user);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to update user");
		}
	}


	private User createUserFromUserBean(UserBean userBean) {
		User user = new User();
		user.setCompany(userBean.getCompany());
		user.seteMail(userBean.geteMail());
		user.setFirstName(userBean.getFirstName());
		user.setId(userBean.getId());
		user.setLastName(userBean.getLastName());
		user.setPassword(userBean.getPassword());
		user.setUsername(userBean.getUsername());
		user.setUserType(userBean.getUserType());
		return user;
	}


	public void userValidations(User user) throws ApplicationException {
		User existingUser = null;

		try {

			existingUser = this.getUser(user.getId());	
		} catch (ApplicationException e) {

			//Allowing the null user through, we handle it later
			if(e.getErrorType().equals(ErrorTypes.INVALID_USER)) {
			}
			else {
				throw e;
			}
		}

		//The null check is intended for when a user is created, so we avoid a null pointer exception
		//that wouldv'e occurred on the second check.
		//The second check is intended for updating a user. If the user isn't changing its name,
		//we must allow it to keep its name, and therefore we skip the name check.
		if (existingUser == null || !user.getUsername().equals(existingUser.getUsername())) {
			if (this.usersDao.existsByUsername(user.getUsername())) {
				throw new ApplicationException(ErrorTypes.INVALID_USERNAME, "Username already taken.");
			}
		}
		
		if (existingUser == null || !user.geteMail().equals(existingUser.geteMail())) {
			if (this.usersDao.existsByEMail(user.geteMail())) {
				throw new ApplicationException(ErrorTypes.INVALID_EMAIL, "E-mail already in use.");
			}
		}
		
		if (existingUser != null && !existingUser.getPassword().equals(String.valueOf(user.getPassword().hashCode()))) {
			passwordValidations(user.getPassword());
		}

		//If the user is a company user then a company must be specified and cannot be null
		if (user.getUserType().equals(UserTypes.COMPANY)) {
			if (user.getCompany() == null) {
				throw new ApplicationException(ErrorTypes.MISSING_COMPANY_ID, "Company id must be specified for a company user.");
			}
			if (!this.companiesController.isCompanyExistsById(user.getCompany().getId())) {
				throw new ApplicationException(ErrorTypes.INVALID_COMPANY, "Company doesn't exists.");
			}
		}
	}


	public void deleteUser(long userId) throws ApplicationException {
		User user = this.getUser(userId);

		//If the user is a customer, we redirect it to the customer controller
		//so it will handle the deletion of both user and customer entities
		if (user.getUserType().equals(UserTypes.CUSTOMER)) {
			this.customersController.deleteCustomer(userId);
			return;
		}
		try {
			this.usersDao.deleteById(userId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete user");
		}
	}


	public User getUser(long id) throws ApplicationException {
		try {
			return this.usersDao.findById(id).get();
		} catch (Exception e) {
			if (e.getCause() == null) {
				throw new ApplicationException(ErrorTypes.INVALID_USER, "User not found");
			}
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get user");
		}
	
	}


	public SuccessfulLoginData login(UserLoginDetails userLoginDetails) throws ApplicationException {
		userLoginDetails.setPassword(String.valueOf(userLoginDetails.getPassword().hashCode()));
		User user = null;

		try {
			if (userLoginDetails.geteMail() != null) {
				user = this.usersDao.findByEMailAndPassword( userLoginDetails.geteMail(), userLoginDetails.getPassword());
			}
			if (userLoginDetails.getUsername() != null) {
				user = this.usersDao.findByUsernameAndPassword(userLoginDetails.getUsername(), userLoginDetails.getPassword());
			}
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Login check failed.");
		}

		if (user == null) {
			throw new ApplicationException(ErrorTypes.INVALID_LOGIN_DETAILS, "Invalid login details.");
		}

		UserLoginData userLoginData = new UserLoginData(user.getId(), user.getUserType());

		//This check is intended to avoid a null pointer exception on user.getCompany().getId()
		if (user.getUserType().equals(UserTypes.COMPANY)) {
			userLoginData.setCompanyId(user.getCompany().getId());
		}
		
		int token = generateToken(userLoginDetails);

		// The following 2 lines are equivalent, just 2 techniques on how to convert an int --> String
		//cacheController.put(token+"", userLoginData);
		cacheController.put(String.valueOf(token), userLoginData);

		return new SuccessfulLoginData(token, userLoginData.getUserType(), user.getFirstName(), user.getLastName());	
	}


	private int generateToken(UserLoginDetails userLoginDetails) {
		String text = userLoginDetails.getUsername() + Calendar.getInstance().getTime().toString() + ENCRYPTION_TOKEN_SALT;
		return text.hashCode();
	}


	public List<User> getAllUsers() throws ApplicationException {
		List<User> users;
		try {
			users = (List<User>) this.usersDao.findAll();
		} catch (Exception e) {
			if (e.getCause() == null) {
				return null;
			}
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get users");
		}
		if (users.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_USER, "Users not found");
		}
		return users;
	}


	public void passwordValidations(String password) throws ApplicationException {
		if (password.length() < 8) {
			throw new ApplicationException(ErrorTypes.INVALID_PASSWORD, "Password too short");
		}

		if (password.length() > 12) {
			throw new ApplicationException(ErrorTypes.INVALID_PASSWORD, "Password too long");
		}

		boolean isCapitalLetterFound = false;
		boolean isLowercaseFound = false;
		boolean isNumberFound = false;
		int index = 0;

		while (index < password.length() && !(isCapitalLetterFound && isLowercaseFound && isNumberFound)) {
			if (password.charAt(index) >= 'A' && password.charAt(index) <= 'Z') {
				isCapitalLetterFound = true;
			}
			if (password.charAt(index) >= 'a' && password.charAt(index) <= 'z') {
				isLowercaseFound = true;
			}
			if (password.charAt(index) >= '0' && password.charAt(index) <= '9') {
				isNumberFound = true;
			}
			index++;
		}

		if (!isCapitalLetterFound) {
			throw new ApplicationException(ErrorTypes.INVALID_PASSWORD, "Password must contain at least one capital letter");
		}

		if (!isLowercaseFound) {
			throw new ApplicationException(ErrorTypes.INVALID_PASSWORD, "Password must contain at least one lowercase letter");
		}

		if (!isNumberFound) {
			throw new ApplicationException(ErrorTypes.INVALID_PASSWORD, "Password must contain at least one number");
		}

	}

}