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
import com.jesus.coupons.entities.Purchase;
import com.jesus.coupons.enums.CouponCategories;
import com.jesus.coupons.exceptions.ApplicationException;
import com.jesus.coupons.logic.PurchasesController;


@RestController
@RequestMapping("/purchases")
public class PurchasesApi {

	
	@Autowired
	private PurchasesController purchasesController;

	
	@PostMapping
	public void createPurchase(@RequestBody Purchase purchase, HttpServletRequest request) throws ApplicationException {
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		this.purchasesController.createPurchase(purchase, userData.getId());
	}

	
	@PutMapping
	public void updatePurchase(@RequestBody Purchase purchase, HttpServletRequest request) throws ApplicationException {
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		this.purchasesController.updatePurchase(purchase, userData.getId());
	}

	
	@GetMapping("{purchaseId}")
	public Purchase getPurchase(@PathVariable("purchaseId") long purchaseId) throws ApplicationException {
		return this.purchasesController.getPurchase(purchaseId);
	}


	@GetMapping()
	public List<Purchase> getAllPurchases() throws ApplicationException {
		return this.purchasesController.getAllPurchases();
	}


	@DeleteMapping("{purchaseId}")
	public void deletePurchase(@PathVariable("purchaseId") long purchaseId) throws ApplicationException {
		this.purchasesController.deletePurchase(purchaseId);
	}

	
	@GetMapping("/getMyPurchases")
	public List<Purchase> getMyPurchases(HttpServletRequest request) throws ApplicationException {
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		return this.purchasesController.findPurchasesByCustomerId(userData.getId());
	}
	
	@GetMapping("/byCustomerId")
	public List<Purchase> findPurchasesByCustomerId(@RequestParam("customerId") long customerId) throws ApplicationException {
		return this.purchasesController.findPurchasesByCustomerId(customerId);
	}

	
	@GetMapping("/byCustomerId/byCategory")
	public List<Purchase> findPurchasesByCustomerIdAndCategory(@RequestParam("customerId") long customerId, 
			@RequestParam("category") CouponCategories category) throws ApplicationException {
		return this.purchasesController.findPurchasesByCustomerIdAndCategory(customerId, category);
	}

	
	@GetMapping("/byCustomerId/byMaxPrice")
	public List<Purchase> findPurchasesByCustomerIdAndMaxPrice(@RequestParam("customerId") long customerId, 
			@RequestParam("maxPrice") float maxPrice) throws ApplicationException {
		return this.purchasesController.findPurchasesByCustomerIdAndMaxPrice(customerId, maxPrice);
	}

	
}