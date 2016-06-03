package com.webapp.tokenbasedauthentication.restapis;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.webapp.tokenbasedauthentication.domain.User;
import com.webapp.tokenbasedauthentication.exceptions.UserNotRegisteredException;
import com.webapp.tokenbasedauthentication.service.TokenService;
import com.webapp.tokenbasedauthentication.service.UserService;
import com.webapp.tokenbasedauthentication.springcontext.SpringContainer;

@Path("authenticate")
public class AuthenticationEndpoint {

	private UserService userService;
	
	private TokenService tokenService;
	
	public AuthenticationEndpoint() {
		userService = (UserService) SpringContainer.getApplicationContext().getBean("userService");
		tokenService = (TokenService) SpringContainer.getApplicationContext().getBean("tokenService");
	}
	
	@POST
	@Path ("")
    @Produces("application/json")
    @Consumes("application/json")
    public Response authenticateUser(User aUser) {

        try {
            authenticate(aUser);
            String token = issueToken(aUser);
            token = "{ \"token\":\"" + token + "\" }";

            return Response.ok(token).build();

        } catch (UserNotRegisteredException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }      
    }

	@POST
	@Path("/check")
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes("application/x-www-form-urlencoded")
    public Response checkAuthenticatedUser(@FormParam("token") String token) {

        try {
            String username = tokenService.getUsernameFromToken(token);
        	
            return Response.ok(username).build();
        } catch (UserNotRegisteredException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }      
    }
	
    private void authenticate(User user) throws UserNotRegisteredException {
        if (!userService.isUserRegistered(user)){
        	throw new UserNotRegisteredException();
        }
    }

    private String issueToken(User aUser) throws UserNotRegisteredException{
        String token = tokenService.giveANewToken(aUser).getToken();
        return token;
    }
}
