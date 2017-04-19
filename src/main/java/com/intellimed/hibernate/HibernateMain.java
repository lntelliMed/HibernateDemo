package com.intellimed.hibernate;

import java.applet.Applet;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.intellimed.hibernate.dto.Address;
import com.intellimed.hibernate.dto.Appliance;
import com.intellimed.hibernate.dto.Property;
import com.intellimed.hibernate.dto.UserDetails;
import com.intellimed.hibernate.dto.Vehicle;

public class HibernateMain {
public static void main(String[] args){
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	UserDetails user = new UserDetails();
	
	//user.setUserID(1);
	//user.setAddress("First user's address");

	user.setUserName("First User");
	user.setJoinedDate(new Date());
	user.setDescription("First user's description goes here");
	
	Address addr = new Address();
	addr.setStreet("Street name");
	addr.setCity("City name");
	
	Address addr2 = new Address();
	addr2.setStreet("Second Street name");
	addr2.setCity("Second City name");
	
	Property property = new Property();
	property.setPropertyName("Condo");
	
	Property property2 = new Property();
	property2.setPropertyName("Apt");
	
	
	Vehicle vehicle = new Vehicle();
	vehicle.setVehicleName("Car");
	
	Appliance appliance = new Appliance();
	appliance.setApplianceName("TV");
	appliance.getUserList().add(user);
	
	/*
	user.setHomeAddress(addr);
	user.setOfficeAddress(addr2);
	*/
	
	user.getListOfAddresses().add(addr);
	user.getListOfAddresses().add(addr2);
	
	user.setVehicle(vehicle);
	
	user.getProperty().add(property);
	user.getProperty().add(property2);
	
	user.getApplianceList().add(appliance);
	
	property.setUser(user);
	property2.setUser(user);
	
	Session session = sessionFactory.openSession();
	session.beginTransaction();
	
	session.save(user);
	session.save(vehicle);
	session.save(property);
	session.save(property2);
	session.save(appliance);
	
	
	session.getTransaction().commit();
	
	session.close();
	
	user = null;
	
	session = sessionFactory.openSession();
	user = (UserDetails) session.get(UserDetails.class, 1);
	
	System.out.println("The retrieved user is " + user.getUserName());
	
	session.close();
	
	//Since there is no open session at this point, this will result in lazy initialization exception unless this collection's fetch type is eager!
	System.out.println(user.getListOfAddresses().size());
	
	
	
	
	
	sessionFactory.close();
	
	
	
}
}
