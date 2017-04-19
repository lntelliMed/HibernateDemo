package com.intellimed.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.intellimed.hibernate.dto.Address;
import com.intellimed.hibernate.dto.UserDetails;

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
	
	/*
	user.setHomeAddress(addr);
	user.setOfficeAddress(addr2);
	*/
	
	user.getListOfAddresses().add(addr);
	user.getListOfAddresses().add(addr2);
	Session session = sessionFactory.openSession();
	session.beginTransaction();
	
	session.save(user);
	session.getTransaction().commit();
	
	session.close();
	
	user = null;
	
	session = sessionFactory.openSession();
	user = (UserDetails) session.get(UserDetails.class, 1);
	
	System.out.println("The retrieved user is " + user.getUserName());
	
	
	
	
	
	
	
	sessionFactory.close();
	
	
	
}
}
