package com.webapp.tokenbasedauthentication.restapi.filtersandinterceptors;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.webapp.tokenbasedauthentication.exceptions.UserNotRegisteredException;
import com.webapp.tokenbasedauthentication.service.TokenService;
import com.webapp.tokenbasedauthentication.springcontext.SpringContainer;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	private TokenService tokenService;
	
	public AuthenticationFilter() {
		tokenService = (TokenService) SpringContainer.getApplicationContext().getBean("tokenService");
	}
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            validateToken(token);
        } catch (UserNotRegisteredException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void validateToken(String token) throws UserNotRegisteredException {
    	tokenService.getUsernameFromToken(token);
    }
}