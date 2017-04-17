package com.intellimed.hibernate.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

//@Entity (name="USER_DETAILS")
@Entity
@Table (name="USER_DETAILS")
public class UserDetails {
	
@Id
//@Column (name="USER_ID")
private int userID;

//@Column (name="USER_NAME")
//@Transient
private String userName;

private String Address;

@Temporal(TemporalType.DATE)
private Date joinedDate;

private String description;




public String getAddress() {
	return Address;
}
public void setAddress(String address) {
	Address = address;
}
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
