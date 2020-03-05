package com.jesus.coupons.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jesus.coupons.enums.CouponCategories;


@Entity
@Table(name="COUPONS")
public class Coupon {

	
	//-------Class Variables---------------------------------------------------------------

	
	@Id
	@GeneratedValue
	private long id;

	@Column(name="TITLE", unique = true, nullable = false)
	private String title;

	@Column(name="PRICE", nullable = false)
	private float price;

	@Column(name="INITIAL_STOCK", nullable = false)
	private int initialStock;

	@Column(name="REMAINING_STOCK", nullable = false)
	private int remainingStock;

	@Column(name="START_DATE", nullable = false)
	private Date startDate;

	@Column(name="EXPIRY_DATE", nullable = false)
	private Date expiryDate;

	@Column(name="CATEGORY", nullable = false)
	private CouponCategories category;
	
	@Column(name="DESCRIPTION", nullable = false)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Company company;

	@OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Purchase> purchase;


	//--------Constructors-----------------------------------------------------------------

	
	public Coupon() {

	}

	
	//----------Setters & Getters-----------------------------------------------------------

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getInitialStock() {
		return initialStock;
	}


	public void setInitialStock(int initialStock) {
		this.initialStock = initialStock;
	}


	public int getRemainingStock() {
		return remainingStock;
	}


	public void setRemainingStock(int remainingStock) {
		this.remainingStock = remainingStock;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getExpiryDate() {
		return expiryDate;
	}


	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}


	public CouponCategories getCategory() {
		return category;
	}


	public void setCategory(CouponCategories category) {
		this.category = category;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public List<Purchase> getPurchase() {
		return purchase;
	}


	public void setPurchase(List<Purchase> purchase) {
		this.purchase = purchase;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", price=" + price + ", initialStock=" + initialStock
				+ ", remainingStock=" + remainingStock + ", startDate=" + startDate + ", expiryDate=" + expiryDate
				+ ", category=" + category + ", description=" + description + ", company=" + company + ", purchase="
				+ purchase + "]";
	}


}