package com.jesus.coupons.logic;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jesus.coupons.dao.IPurchasesDao;
import com.jesus.coupons.entities.Coupon;
import com.jesus.coupons.entities.Customer;
import com.jesus.coupons.entities.Purchase;
import com.jesus.coupons.enums.CouponCategories;
import com.jesus.coupons.enums.ErrorTypes;
import com.jesus.coupons.exceptions.ApplicationException;


@Controller
public class PurchasesController {

	@Autowired
	private IPurchasesDao purchasesDao;

	@Autowired
	private CouponsController couponsController;

	@Autowired
	private CustomersController customersController;

	
	public PurchasesController() {
	}


	public void createPurchase(Purchase purchase, long customerId) throws ApplicationException {
		Coupon coupon = this.couponsController.getCoupon(purchase.getCoupon().getId());

		purchase = setPurchaseDetails(purchase, coupon, customerId);
		
		this.purchaseValitation(purchase, coupon);
		try {
			this.purchasesDao.save(purchase);
			
			//Updating the remaining stock for the purchased coupon
			coupon.setRemainingStock(coupon.getRemainingStock() - purchase.getAmountOfItems());
			this.couponsController.updateCoupon(coupon, coupon.getCompany().getId());
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to create new purchase");
		}
	}


	public void updatePurchase(Purchase purchase, long customerId) throws ApplicationException {
		Coupon coupon = this.couponsController.getCoupon(purchase.getCoupon().getId());
		
		purchase = setPurchaseDetails(purchase, coupon, customerId);
		
		this.purchaseValitation(purchase, coupon);
		try {
			this.purchasesDao.save(purchase);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to update purchase");
		}
	}


	private Purchase setPurchaseDetails(Purchase purchase, Coupon coupon, long customerId) throws ApplicationException {
		
		//Setting the customer's id
		purchase.setCustomer(new Customer());
		purchase.getCustomer().setId(customerId);

		//Setting the total price
		purchase.setTotalPrice(coupon.getPrice() * purchase.getAmountOfItems());

		//Setting the time stamp 
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		purchase.setTimestamp(timestamp);
		return purchase;
	}


	private void purchaseValitation(Purchase purchase, Coupon coupon) throws ApplicationException {
		if (!this.couponsController.isCouponExistsById(purchase.getCoupon().getId())) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON, "Coupon doesn't exists.");
		}
		if (!this.customersController.isCustomerExistsById(purchase.getCustomer().getId())) {
			throw new ApplicationException(ErrorTypes.INVALID_CUSTOMER, "Customer doesn't exists.");
		}
		if (coupon.getRemainingStock() < 1) {
			throw new ApplicationException(ErrorTypes.COUPON_OUT_OF_STOCK, "Coupon out of stock.");
		}
		if (purchase.getAmountOfItems() < 1) {
			throw new ApplicationException(ErrorTypes.INVALID_AMOUNT_OF_ITEMS, "Amount must be above 0.");
		}
		if (coupon.getRemainingStock() - purchase.getAmountOfItems() < 0) {
			throw new ApplicationException(ErrorTypes.NOT_ENOUGH_ITEMS_IN_STOCK, "Not enough items in stock, please choose a different amount.");
		}
	}

	
	public void deletePurchase(long purchaseId) throws ApplicationException {
		try {
			this.purchasesDao.deleteById(purchaseId);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to delete purchase");
		}
	}


	public Purchase getPurchase(long purchaseId) throws ApplicationException {
		try {
			return this.purchasesDao.findById(purchaseId).get();
		} catch (Exception e) {
			if (e.getCause() == null) {
				throw new ApplicationException(ErrorTypes.INVALID_PURCHASE, "Purchase not found");
			}
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get purchase");
		}
		
	}


	public List<Purchase> getAllPurchases() throws ApplicationException {
		List<Purchase> purchases;
		try {
			purchases = (List<Purchase>) this.purchasesDao.findAll();
		} catch (Exception e) {		
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get purchase");
		}

		if (purchases.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_PURCHASE, "Purchases not found");
		}
		return purchases;
	}

	public List<Purchase> findPurchasesByCustomerId(long customerId) throws ApplicationException {
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println(customerId);
		List<Purchase> purchases;
		try {
			purchases = this.purchasesDao.findByCustomerId(customerId);
		} catch (Exception e) {

			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get purchases");
		}

		if (purchases.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_PURCHASE, "Purchases not found");
		}
		return purchases;
	}


	public List<Purchase> findPurchasesByCustomerIdAndCategory(long customerId, CouponCategories category) throws ApplicationException {
		List<Purchase> purchases;
		try {
			purchases = this.purchasesDao.findByCustomerIdAndCouponCategory(customerId, category);
		} catch (Exception e) {

			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get purchases");
		}
		if (purchases.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_PURCHASE, "Purchases not found");
		}
		return purchases;
	}


	public List<Purchase> findPurchasesByCustomerIdAndMaxPrice(long customerId, float maxPrice) throws ApplicationException {
		List<Purchase> purchases;
		try {
			purchases =  this.purchasesDao.findByCustomerIdAndCouponPriceLessThanEqual(customerId, maxPrice);
		} catch (Exception e) {

			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to get purchases");
		}

		if (purchases.isEmpty()) {
			throw new ApplicationException(ErrorTypes.INVALID_PURCHASE, "Purchases not found");
		}
		return purchases;
	}


}