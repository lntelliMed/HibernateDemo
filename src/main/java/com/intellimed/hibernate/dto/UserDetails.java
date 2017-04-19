package com.intellimed.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

//@Entity (name="USER_DETAILS")
@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @Column (name="USER_ID")
	// @EmbeddedId //Can be used if the primary key was a composite/another
	// object instead..
	private int userID;

	// @Column (name="USER_NAME")
	// @Transient
	private String userName;

	@OneToOne
	@JoinColumn(name = "VEHICLE_ID")
	private Vehicle vehicle;

	//@OneToMany
	//@JoinTable(name = "USER_PROPERTY", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "VEHICLE_ID"))
	@OneToMany(mappedBy="user")
	private Collection<Property> property = new ArrayList<Property>();
	
	@ManyToMany
	private Collection<Appliance> applianceList = new ArrayList<Appliance>();
 

	// private String Address;
	/*
	 * @Embedded
	 * 
	 * @AttributeOverrides({ @AttributeOverride(name = "street", column
	 * = @Column(name = "HOME_STREET_NAME")),
	 * 
	 * @AttributeOverride(name = "city", column = @Column(name =
	 * "HOME_CITY_NAME")),
	 * 
	 * @AttributeOverride(name = "state", column = @Column(name =
	 * "HOME_STATE_NAME")),
	 * 
	 * @AttributeOverride(name = "zipcode", column = @Column(name =
	 * "HOME_ZIP_CODE")),
	 * 
	 * @AttributeOverride(name = "country", column = @Column(name =
	 * "HOME_COUNTRY_NAME")), }
	 * 
	 * ) private Address homeAddress;
	 * 
	 * @Embedded private Address officeAddress;
	 * 
	 * public Address getHomeAddress() { return homeAddress; }
	 * 
	 * public void setHomeAddress(Address homeAddress) { this.homeAddress =
	 * homeAddress; }
	 * 
	 * public Address getOfficeAddress() { return officeAddress; }
	 * 
	 * public void setOfficeAddress(Address officeAddress) { this.officeAddress
	 * = officeAddress; }
	 * 
	 */

	public Collection<Appliance> getApplianceList() {
		return applianceList;
	}

	public void setApplianceList(Collection<Appliance> applianceList) {
		this.applianceList = applianceList;
	}

	public Collection<Property> getProperty() {
		return property;
	}

	public void setProperty(Collection<Property> property) {
		this.property = property;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	// To add a primary key, change to Collection which supports index.
	// private Set<Address> listOfAddresses = new HashSet<Address>();
	@JoinTable(name = "USER_ADDRESS", joinColumns = @JoinColumn(name = "USER_ID"))
	@GenericGenerator(name = "hilo-gen", strategy = "hilo")
	@CollectionId(columns = { @Column(name = "ADDRESS_ID") }, generator = "hilo-gen", type = @Type(type = "long"))
	private Collection<Address> listOfAddresses = new ArrayList<Address>();

	public Collection<Address> getListOfAddresses() {
		return listOfAddresses;
	}

	public void setListOfAddresses(Collection<Address> listOfAddresses) {
		this.listOfAddresses = listOfAddresses;
	}

	@Temporal(TemporalType.DATE)
	private Date joinedDate;

	private String description;

	public Date getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
