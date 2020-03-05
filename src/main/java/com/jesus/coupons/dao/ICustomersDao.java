package com.jesus.coupons.dao;

import org.springframework.data.repository.CrudRepository;

import com.jesus.coupons.entities.Customer;


public interface ICustomersDao extends CrudRepository<Customer, Long> {

	
}