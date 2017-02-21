package com.packt.storyworld.repository.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.packt.storyworld.domain.sql.User;
import com.packt.storyworld.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getLogonUsers() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.isNotNull("token"));
		List<User> listOfUsers = new LinkedList<>(criteria.list());
		session.close();
		return listOfUsers;
	}

	@Override
	public void update(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public User getUserByName(String name) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("name", name));
		List<User> listOfUser = new ArrayList<>(0);
		listOfUser = criteria.list();
		User user = new User();
		if (!listOfUser.isEmpty())
			user = listOfUser.get(0);
		session.close();
		return user;
	}

	@Override
	public User register(User user) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			return user;
		} catch (Exception e) {
			session.getTransaction().rollback();
			log.error("Error when save user ", e);
		} finally {
			session.close();
		}
		return null;
	}

}
