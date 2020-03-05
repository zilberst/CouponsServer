package com.jesus.coupons.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jesus.coupons.entities.Purchase;
import com.jesus.coupons.enums.CouponCategories;


public interface IPurchasesDao extends CrudRepository<Purchase, Long> {

	
	public List<Purchase> findByCouponId(long couponId);

	public List<Purchase> findByCustomerId(long customerId);

	public List<Purchase> findByCustomerIdAndCouponCategory(long customerId, CouponCategories category);

	public List<Purchase> findByCustomerIdAndCouponPriceLessThanEqual(long customerId, float price);

	
}