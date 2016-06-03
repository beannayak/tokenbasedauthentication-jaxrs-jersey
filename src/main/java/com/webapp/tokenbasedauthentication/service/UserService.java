package com.webapp.tokenbasedauthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.tokenbasedauthentication.dao.UserDao;
import com.webapp.tokenbasedauthentication.domain.User;
import com.webapp.tokenbasedauthentication.exceptions.UserAlreadyRegisteredException;

@Service
@Transactional (propagation = Propagation.REQUIRES_NEW)
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public boolean registerUser (User user) throws UserAlreadyRegisteredException {
		if (isUserRegistered(user)) {
			throw new UserAlreadyRegisteredException();
		}
		
		//user.setPassword(userBCryptHash(user.getPassword()));
		userDao.save(user);
		return true; 
	}
	
	public boolean isUserRegistered(User user) {
		User userFromDatabase = userDao.getUserByUsername(user.getUsername());
		if (userFromDatabase != null) {
			return true;
		}
		return false;
	}
	
	public User getManagedUser(User aUser) {
		User managedUser = userDao.getUserByUsername(aUser.getUsername());
		if (managedUser.getPassword().equals(aUser.getPassword())) {
			return managedUser;
		} 
		return null;
	}
}
