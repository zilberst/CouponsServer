package com.jesus.coupons.dao;

import org.springframework.data.repository.CrudRepository;

import com.jesus.coupons.entities.User;


public interface IUsersDao extends CrudRepository<User, Long> {
	
	public void deleteByUsername(String username);

	public User findByUsername(String username);
	
	public boolean existsByUsername(String username);
	
	public User findByUsernameAndPassword(String username, String password);
	
	public User findByEMailAndPassword(String eMail, String password);

	public boolean existsByEMail(String eMail);

	
}