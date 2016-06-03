package com.webapp.tokenbasedauthentication.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.tokenbasedauthentication.domain.User;

@Repository
@Transactional (propagation=Propagation.MANDATORY)
public class UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(User user) {
		sessionFactory.getCurrentSession().save(user);
	}
	
	public User merge(User user) {
		return (User) sessionFactory.getCurrentSession().merge(user);
	}
	
	public User getUserById(long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}
	
	public User getUserByUsername (String username) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Users where username = :uname");
		query.setParameter("uname", username);
		
		@SuppressWarnings("unchecked")
		List<User> users = query.list();
	        if (users.size() >= 1){
	            return users.get(0);
	        } 
	        return null;
	}
}
