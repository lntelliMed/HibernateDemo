package com.intellimed.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Property {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int propertyId;
	private String propertyName;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	@NotFound(action=NotFoundAction.IGNORE) //To suppress any Hibernate errors if no user is defined for a property
	private UserDetails user;
	

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
