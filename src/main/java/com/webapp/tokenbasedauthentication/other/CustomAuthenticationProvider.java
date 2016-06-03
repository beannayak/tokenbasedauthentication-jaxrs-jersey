package com.webapp.tokenbasedauthentication.other;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.tokenbasedauthentication.domain.User;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private SessionFactory sf;

	public CustomAuthenticationProvider() {
	}

	public CustomAuthenticationProvider(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {		
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		List<GrantedAuthority> grantedAuths = new ArrayList<>();

		Session session;
        User userCredentials = null;
        try {
            session = sf.openSession();
            Transaction tx = session.beginTransaction();
            
            Query query = session.createQuery("from Users where username = :uname");
            query.setParameter("uname", name);
            userCredentials = (User) query.list().get(0);
            
            tx.commit();
        } catch (Exception e) {
            System.out.println("Exception occured: " + e.getMessage());
        }
		
        if (userCredentials != null) {
        	grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
        	throw new BadCredentialsException("Bad Credentials");
        }
        
		Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);

		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}