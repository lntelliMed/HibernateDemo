package com.intellimed.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.intellimed.hibernate.dto.UserDetails;

public class HibernateMain {
public static void main(String[] args){
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	UserDetails user = new UserDetails();
	
	user.setUserID(1);
	user.setUserName("First User");
	
	Session session = sessionFactory.openSession();
	session.beginTransaction();
	
	session.save(user);
	session.getTransaction().commit();
	
	session.close();
	sessionFactory.close();
	
	
	
}
}
