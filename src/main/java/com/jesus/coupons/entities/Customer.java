package com.jesus.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jesus.coupons.enums.MartialStatus;


@Entity
@Table(name="CUSTOMERS")
public class Customer {

	
	//-------Class Variables---------------------------------------------------------------

	
	@Id
	private long id;

	@Column(name="AMOUNT_OF_KIDS", nullable = true)
	private int amountOfKids;

	@Column(name="MARTIAL_STATUS", nullable = false)
	private MartialStatus martialStatus;

	@Column(name="ADDRESS", nullable = false)
	private String address;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@MapsId 
	private User user;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Purchase> purchases;

	
	//--------Constructors-----------------------------------------------------------------

	
	public Customer() {

	}

	
	//----------Setters & Getters-----------------------------------------------------------
	
	
	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	
	public int getAmountOfKids() {
		return amountOfKids;
	}

	
	public void setAmountOfKids(int amountOfKids) {
		this.amountOfKids = amountOfKids;
	}
	
	public String getAddress() {
		return address;
	}

	
	public void setAddress(String address) {
		this.address = address;
	}

	
	public User getUser() {
		return user;
	}

	
	public void setUser(User user) {
		this.user = user;
	}

	
	public List<Purchase> getPurchases() {
		return purchases;
	}

	
	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public MartialStatus getMartialStatus() {
		return martialStatus;
	}

	public void setMartialStatus(MartialStatus martialStatus) {
		this.martialStatus = martialStatus;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", amountOfKids=" + amountOfKids + ", martialStatus=" + martialStatus
				+ ", address=" + address + ", user=" + user + ", purchases=" + purchases + "]";
	}



}