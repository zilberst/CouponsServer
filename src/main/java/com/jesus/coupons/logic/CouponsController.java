package com.jesus.coupons.logic;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jesus.coupons.enums.CouponCategories;
import com.jesus.coupons.enums.ErrorTypes;
import com.jesus.coupons.exceptions.ApplicationException;
import com.jesus.coupons.dao.ICouponsDao;
import com.jesus.coupons.entities.Company;
import com.jesus.coupons.entities.Coupon;


@Controller
public class CouponsController {

	
	@Autowired
	private ICouponsDao couponsDao;


	public CouponsController() {
	}


	public void createCoupon(Coupon coupon, long companyId) throws ApplicationException {
		coupon.setCompany(new Company());
		coupon.getCompany().setId(companyId);
		coupon.setRemainingStock(coupon.getInitialStock());

		couponValidations(coupon);
		try {
			this.couponsDao.save(coupon);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to create new coupon");
		}
	}


	public void updateCoupon(Coupon coupon, long companyId) throws ApplicationException {
		coupon.setCompany(new Company());
		coupon.getCompany().setId(companyId);
		if (!this.isCouponExistsById(coupon.getId())) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON, "Coupon doesn't exist");
		}

		couponValidations(coupon);
		try {
			this.couponsDao.save(coupon);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to update coupon");
		}
	}


	private void couponValidations(Coupon coupon) throws ApplicationException {
		Coupon existingCoupon = null;

		try {

			existingCoupon = this.getCoupon(coupon.getId());
		} catch (ApplicationException e) {

			//Allowing the null coupon through, we handle it later
			if(e.getErrorType().equals(ErrorTypes.INVALID_COUPON)) {
			}
			else {
				throw e;
			}
		}

		//The null check is intended for when a coupon is created, so we avoid a null pointer exception
		//that wouldv'e occurred on the second check.
		//The second check is intended for updating a coupon. If the coupon isn't changing its name,
		//we must allow it to keep its name, and therefore we skip the name check.
		if (existingCoupon == null || !coupon.getTitle().equals(existingCoupon.getTitle())) {
			if (this.couponsDao.existsByTitle(coupon.getTitle())) {
				throw new ApplicationException(ErrorTypes.INVALID_COUPON_NAME, "Coupon name already exists.");
			}
		}
		
		if (coupon.getPrice() < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_PRICE, "Price must be greater than 0.");
		}
		

		Date today = new Date(Calendar.getInstance().getTime().getTime());
		
		if ((existingCoupon == null || 
				!coupon.getStartDate().toString().equals(existingCoupon.getStartDate().toString())) &&
				coupon.getStartDate().compareTo(today) < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_START_DATE, "Start date has already passed.");
		}
		
		if (coupon.getExpiryDate().compareTo(coupon.getStartDate()) < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_END_DATE, "End date cannot be before the start date.");
		}
		
		if (existingCoupon == null && coupon.getInitialStock() < 1) {
			throw new ApplicationException(ErrorTypes.INVALID_STOCK, "Initial stock must be above 0.");
		}
	}


	public void deleteCoupon(long couponId) throws ApplicationException {
		try {
			this.couponsDao.deleteById(couponId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete coupon");
		}
	}


	public Coupon getCoupon(long couponId) throws ApplicationException {
		try {
			return this.couponsDao.findById(couponId).get();
		} catch (Exception e) {
			if (e.getCause() == null) {
				throw new ApplicationException(ErrorTypes.INVALID_COUPON, "Coupon not found");
			}
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get coupon");
		}

	}


	public List<Coupon> getAllCoupons() throws ApplicationException {
		List<Coupon> coupons;
		try {
			coupons = (List<Coupon>) this.couponsDao.findAll();
		} catch (Exception e) {

			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get coupons");
		}

		if (coupons.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON, "Coupons not found");
		}
		return coupons;
	}


	public List<Coupon> findCouponsByCompanyId(long companyId) throws ApplicationException {
		List<Coupon> coupons;
		try {
			coupons = this.couponsDao.findByCompanyId(companyId);
		} catch (Exception e) {

			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get coupons");
		}

		if (coupons.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON, "Coupons not found");
		}
		return coupons;
	}


	public List<Coupon> findCouponsByCategory(CouponCategories category) throws ApplicationException {
		List<Coupon> coupons;
		try {
			coupons = this.couponsDao.findByCategory(category);
		} catch (Exception e) {

			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get coupons");
		}

		if (coupons.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON, "Coupons not found");
		}
		return coupons;
	}


	public List<Coupon> findCouponsByMaxPrice(float maxPrice) throws ApplicationException {
		List<Coupon> coupons;
		try {
			coupons = this.couponsDao.findByPriceLessThanEqual(maxPrice);
		} catch (Exception e) {

			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get coupons");
		}

		if (coupons.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON, "Coupons not found");
		}
		return coupons;
	}


	public boolean isCouponExistsById(long couponId) throws ApplicationException {
		try {
			return this.couponsDao.existsById(couponId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to check id");
		}
	}


	public void deleteExpiredCoupons() {
		Date today = new Date(Calendar.getInstance().getTime().getTime());
		synchronized (couponsDao) {
			this.couponsDao.deleteByExpiryDateLessThan(today);
		}
	}


}