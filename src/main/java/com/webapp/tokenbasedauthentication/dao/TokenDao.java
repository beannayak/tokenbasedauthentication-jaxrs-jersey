package com.webapp.tokenbasedauthentication.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.tokenbasedauthentication.domain.Token;

@Repository
@Transactional (propagation = Propagation.MANDATORY)
public class TokenDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(Token token) {
		sessionFactory.getCurrentSession().save(token);
	}
	
	public Token merge(Token token) {
		return (Token) sessionFactory.getCurrentSession().merge(token);
	}
	
	@SuppressWarnings("unchecked")
	public Token getTokenByUserId (long id) {
		Token token = null;
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT t FROM Tokens t JOIN t.user u WHERE u.id = :id");
		query.setParameter("id", id);
		List<Token> tokens = query.list(); 
		
		if (tokens.size() >= 1) {
			token = tokens.get(0);
		}
		
		return token;
	}
	
	@SuppressWarnings("unchecked")
	public Token getTokenByUsername (String username) {
		Token token = null;
		
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT t FROM Tokens t JOIN t.user u WHERE u.username = :name");
		query.setParameter("name", username);
		List<Token> tokens = query.list(); 
		
		if (tokens.size() >= 1) {
			token = tokens.get(0);
		}
		
		return token;
	}
	
	public void deleteToken (Token token) {
		sessionFactory.getCurrentSession().delete(token);
	}

	@SuppressWarnings("unchecked")
	public boolean tokenExists(String sToken) {		
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Tokens t WHERE t.token = :token");
		query.setParameter("token", sToken);
		List<Token> tokens = query.list(); 
		
		if (tokens.size() > 0) {
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public Token getToken(String sToken) {		
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Tokens t WHERE t.token = :token");
		query.setParameter("token", sToken);
		List<Token> tokens = query.list(); 
		
		if (tokens.size() > 0) {
			return tokens.get(0);
		}
		
		return null;
	}
}
