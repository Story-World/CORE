package com.packt.storyworld.repository.impl;

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

import com.packt.storyworld.domain.User;
import com.packt.storyworld.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@SuppressWarnings("unused")
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

}
