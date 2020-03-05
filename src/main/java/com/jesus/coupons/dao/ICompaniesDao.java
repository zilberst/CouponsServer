package com.jesus.coupons.dao;

import org.springframework.data.repository.CrudRepository;

import com.jesus.coupons.entities.Company;


public interface ICompaniesDao extends CrudRepository<Company, Long> {	

	
	public boolean existsByName(String name);


}