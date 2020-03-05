package com.jesus.coupons.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jesus.coupons.utils.JsonTimestampSerializer;


@Entity
@Table(name="PURCHASES")
public class Purchase {

	
	//-------Class Variables---------------------------------------------------------------

	
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;

	@Column(name="AMOUNT_OF_ITEMS", nullable = false)
	private int amountOfItems;

	@Column(name="TOTAL_PRICE", nullable = false)
	private float totalPrice;

	@Column(name="TIMESTAMP", nullable = false)
	private Timestamp timestamp;

	@ManyToOne(fetch = FetchType.EAGER)
	private Coupon coupon;

	
	//--------Constructors-----------------------------------------------------------------
	

	public Purchase() {

	}
	
	
	//----------Setters & Getters-----------------------------------------------------------

	
	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	
	public Customer getCustomer() {
		return customer;
	}

	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	public int getAmountOfItems() {
		return amountOfItems;
	}

	
	public void setAmountOfItems(int amountOfItems) {
		this.amountOfItems = amountOfItems;
	}

	
	public float getTotalPrice() {
		return totalPrice;
	}

	
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	
	@JsonSerialize(using=JsonTimestampSerializer.class)
	public Timestamp getTimestamp() {
		return timestamp;
	}

	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	
	public Coupon getCoupon() {
		return coupon;
	}

	
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	
	@Override
	public String toString() {
		return "Purchase [id=" + id + ", customer=" + customer + ", amountOfItems=" + amountOfItems + ", totalPrice="
				+ totalPrice + ", timestamp=" + timestamp + ", coupon=" + coupon + "]";
	}

	
}