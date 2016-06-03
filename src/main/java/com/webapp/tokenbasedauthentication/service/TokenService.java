package com.webapp.tokenbasedauthentication.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.tokenbasedauthentication.dao.TokenDao;
import com.webapp.tokenbasedauthentication.domain.Token;
import com.webapp.tokenbasedauthentication.domain.User;
import com.webapp.tokenbasedauthentication.exceptions.UserNotRegisteredException;

@Service
@Transactional (propagation = Propagation.REQUIRES_NEW)
public class TokenService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenDao tokenDao;
	
	public Token giveANewToken(User user) throws UserNotRegisteredException {
		Token token = tokenDao.getTokenByUsername(user.getUsername());
		if (token != null) {
			tokenDao.deleteToken(token);
		}
		
		token = new Token();
		User managedUser = userService.getManagedUser(user);
		if (managedUser == null) {
			throw new UserNotRegisteredException();
		}
		token.setUser(managedUser);
		
		String sToken = null;
		do {
			Random random = new SecureRandom();
			sToken = new BigInteger(130, random).toString(32);
		} while (tokenDao.tokenExists(sToken));
	    
		token.setToken(sToken);
		tokenDao.save(token);
	    
	    return token;
	}
	
	public String getUsernameFromToken(String sToken) throws UserNotRegisteredException {
		Token token = tokenDao.getToken(sToken);
		if (token == null) {
			throw new UserNotRegisteredException();
		}
		return token.getUser().getUsername();
	}
}
