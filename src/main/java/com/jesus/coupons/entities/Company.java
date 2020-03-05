package com.jesus.coupons.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jesus.coupons.enums.CompanyTypes;


@Entity
@Table(name="COMPANIES")
public class Company {

	
	//-------Class Variables---------------------------------------------------------------
	
	
	@Id
	@GeneratedValue
	private long id;

	@Column(name="NAME", unique = true, nullable = false)
	private String name;

	@Column(name="ADDRESS", nullable = false)
	private String address;

	@Column(name="PHONE", nullable = false)
	private String phone;

	@Column(name="TYPE", nullable = false)
	private CompanyTypes type;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Coupon> coupons;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<User> users;


	//--------Constructors------------------------------------------------------------------

	
	public Company() {

	}

	
	//----------Setters & Getters-----------------------------------------------------------

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public CompanyTypes getType() {
		return type;
	}


	public void setType(CompanyTypes type) {
		this.type = type;
	}


	public List<Coupon> getCoupons() {
		return coupons;
	}


	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}

	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", type=" + type
				+ ", coupons=" + coupons + ", users=" + users + "]";
	}


}