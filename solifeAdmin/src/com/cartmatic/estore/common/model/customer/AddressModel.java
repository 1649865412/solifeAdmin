package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.system.AppUser;

public class AddressModel {
	private Integer addressId;
	private String title;
	private String firstname;
	private String lastname;

	private String address;
	private String address2;

	private String countryName;
	private String stateName;
	private String cityName;
	private String sectionName;

	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	
	private String zip;
	private String email;
	private String telephone;
	
	private String b_title;
	private String b_firstname;
	private String b_lastname;

	private String b_address;
	private String b_address2;

	private String b_countryName;
	private String b_stateName;
	private String b_cityName;

	private Integer b_countryId;
	private Integer b_stateId;
	private Integer b_cityId;
	
	private String b_zip;
	private String b_telephone;
	
	private AppUser appUser;

	/**
	 * 欺骗通过属性
	 */
	private Integer id;
	
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public String getTitle() {   
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_firstname() {
		return b_firstname;
	}
	public void setB_firstname(String b_firstname) {
		this.b_firstname = b_firstname;
	}
	public String getB_lastname() {
		return b_lastname;
	}
	public void setB_lastname(String b_lastname) {
		this.b_lastname = b_lastname;
	}
	public String getB_address() {
		return b_address;
	}
	public void setB_address(String b_address) {
		this.b_address = b_address;
	}
	public String getB_address2() {
		return b_address2;
	}
	public void setB_address2(String b_address2) {
		this.b_address2 = b_address2;
	}
	public String getB_countryName() {
		return b_countryName;
	}
	public void setB_countryName(String name) {
		b_countryName = name;
	}
	public String getB_stateName() {
		return b_stateName;
	}
	public void setB_stateName(String name) {
		b_stateName = name;
	}
	public String getB_cityName() {
		return b_cityName;
	}
	public void setB_cityName(String name) {
		b_cityName = name;
	}
	public Integer getB_countryId() {
		return b_countryId;
	}
	public void setB_countryId(Integer id) {
		b_countryId = id;
	}
	public Integer getB_stateId() {
		return b_stateId;
	}
	public void setB_stateId(Integer id) {
		b_stateId = id;
	}
	public Integer getB_cityId() {
		return b_cityId;
	}
	public void setB_cityId(Integer id) {
		b_cityId = id;
	}
	public String getB_zip() {
		return b_zip;
	}
	public void setB_zip(String b_zip) {
		this.b_zip = b_zip;
	}
	public String getB_telephone() {
		return b_telephone;
	}
	public void setB_telephone(String b_telephone) {
		this.b_telephone = b_telephone;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public AppUser getAppUser() {
		return appUser;
	}
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	
	
}
