package com.intellimed.hibernate;

import java.applet.Applet;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.intellimed.hibernate.dto.Address;
import com.intellimed.hibernate.dto.Animal;
import com.intellimed.hibernate.dto.Appliance;
import com.intellimed.hibernate.dto.Cat;
import com.intellimed.hibernate.dto.Property;
import com.intellimed.hibernate.dto.UserDetails;
import com.intellimed.hibernate.dto.Vehicle;

public class HibernateMain {
	public static void main(String[] args) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		UserDetails user = new UserDetails();

		// user.setUserID(1);
		// user.setAddress("First user's address");

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
		 * user.setHomeAddress(addr); user.setOfficeAddress(addr2);
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

		// session.persist(user);//Will work with the
		// "@OneToMany(cascade=CascadeType.PERSIST)" so that we don't have to
		// save the individual objects tied to user (as with the need for using
		// save(property)/save(property2) below.

		session.save(user); // Object is in transient state before the save.
							// After save it becomes Persistent.
		user.setUserName("Updated user name (Persistent)");

		session.save(vehicle);
		session.save(property);
		session.save(property2);
		session.save(appliance);

		// Single table inheritance!
		Animal animal = new Animal();
		animal.setAnimalName("Animal");
		session.save(animal);
		Cat cat = new Cat();
		cat.setAnimalName("Cat");
		cat.setHairColor("Blonde");

		session.save(cat);

		session.getTransaction().commit();

		session.close();

		user = null;

		session = sessionFactory.openSession();
		user = (UserDetails) session.get(UserDetails.class, 1);

		// session.delete(user);
		// user.setUserName("Updated name");
		// session.update(user);

		System.out.println("The retrieved user is " + user.getUserName());

		session.close();

		// Since there is no open session at this point, this will result in
		// lazy initialization exception unless this collection's fetch type is
		// eager!
		System.out.println(user.getListOfAddresses().size());

		System.out.println("The retrieved user is " + user.getUserName());

		user.setUserName("Updated user name (Detached)");// Detached state. Will
															// not be reflected
															// in database
															// unless the
															// session is opened
															// again and the
															// update is done as
															// shown below..

		// Instead of keeping the session open to wait on user, just reopen
		// again and update the object whenever user finishes the updates! Below
		// will make the object persistent again
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();

		// HQL
		session = sessionFactory.openSession();
		// session.beginTransaction();
		String userId = "1";
		// Query query = session.createQuery("from UserDetails where userid >=
		// ?");
		// query.setInteger(0, Integer.parseInt(userId));

		Query query = session.createQuery("from UserDetails where userid >= :userId");
		query.setCacheable(true);
		query.setInteger("userId", Integer.parseInt(userId));

		List<UserDetails> users = (List<UserDetails>) query.list();

		for (UserDetails userRecord : users) {
			System.out.println(userRecord.getUserName());
		}
		// session.getTransaction().commit();

		// Named Query
		query = session.getNamedQuery("UserDetails.byName");
		query.setString("userName", "Updated user name (Detached)");
		List<UserDetails> users2 = (List<UserDetails>) query.list();

		for (UserDetails userRecord : users2) {
			System.out.println(userRecord.getUserID());
		}

		// Named Native Query
		query = session.getNamedQuery("UserDetails.byId");
		query.setInteger("userId", 1);
		UserDetails userObj = (UserDetails) query.uniqueResult();
		System.out.println(userObj.getUserName());

		
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("userID", 1)).add(Restrictions.like("userName", "%user%"));
		List<UserDetails> users3 = (List<UserDetails>) criteria.list();

		for (UserDetails userRecord : users3) {
			System.out.println("Using criteria: " + userRecord.getUserID());
		}
		
		criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.or(Restrictions.eq("userID", 1), Restrictions.gt("userID", 1))).setProjection(Projections.property("userName")).addOrder(Order.asc("userID"));
		List<String> usersNames = (List<String>) criteria.list();

		for (String userName : usersNames) {
			System.out.println("User names using criteria: " + userName);
		}
		
		
		UserDetails exampleUser = new UserDetails();
		exampleUser.setUserID(1);
		
		Example example = Example.create(exampleUser);
		
		criteria = session.createCriteria(UserDetails.class).add(example);

		List<UserDetails> users4 = (List<UserDetails>) criteria.list();

		for (UserDetails userRecord : users4) {
			System.out.println("Using query by example: " + userRecord.getUserName());
		}
		sessionFactory.close();

	}
}
