package com.jesus.coupons.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jesus.coupons.entities.Coupon;
import com.jesus.coupons.enums.CouponCategories;


public interface ICouponsDao extends CrudRepository<Coupon, Long> {

	
	public List<Coupon> findByCompanyId(long id);

	public List<Coupon> findByCategory(CouponCategories category);

	public List<Coupon> findByPriceLessThanEqual(float price);

	public boolean existsByTitle(String title);

	public boolean existsById(long couponId);
	
	@Transactional
	public void deleteByExpiryDateLessThan(Date date);

}