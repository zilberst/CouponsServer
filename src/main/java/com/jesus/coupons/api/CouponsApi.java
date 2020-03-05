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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jesus.coupons.data.UserLoginData;
import com.jesus.coupons.entities.Coupon;
import com.jesus.coupons.enums.CouponCategories;
import com.jesus.coupons.enums.ErrorTypes;
import com.jesus.coupons.enums.UserTypes;
import com.jesus.coupons.exceptions.ApplicationException;
import com.jesus.coupons.logic.CouponsController;


@RestController
@RequestMapping("/coupons")
public class CouponsApi {

	
	@Autowired
	private CouponsController couponsController;


	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon, HttpServletRequest request) throws ApplicationException {
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		if (userData.getUserType().equals(UserTypes.COMPANY)) {
			this.couponsController.createCoupon(coupon, userData.getCompanyId());
		}
		else {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED, "Must be a company user.");
		}
	}

	
	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon, HttpServletRequest request) throws ApplicationException {
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		this.couponsController.updateCoupon(coupon, userData.getCompanyId());
	}

	
	@GetMapping("{couponId}")
	public Coupon getCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		return this.couponsController.getCoupon(couponId);
	}


	@GetMapping
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return this.couponsController.getAllCoupons();
	}


	@DeleteMapping("{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		this.couponsController.deleteCoupon(couponId);
	}

	
	@GetMapping("/byCompany")
	public List<Coupon> getCompanyCoupons(@RequestParam("companyId") long companyId) throws ApplicationException {
		return this.couponsController.findCouponsByCompanyId(companyId);
	}

	
	@GetMapping("/byCategory")
	public List<Coupon> getCompanyCoupons(@RequestParam("category") CouponCategories category) throws ApplicationException {
		return this.couponsController.findCouponsByCategory(category);
	}

	
	@GetMapping("/byMaxPrice")
	public List<Coupon> getCompanyCoupons(@RequestParam("maxPrice") float maxPrice) throws ApplicationException {
		return this.couponsController.findCouponsByMaxPrice(maxPrice);
	}


}