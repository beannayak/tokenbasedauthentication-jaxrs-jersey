package com.webapp.tokenbasedauthentication.restapis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.webapp.tokenbasedauthentication.restapi.filtersandinterceptors.Secured;


@Path("home")
public class JerseyRoot {
	
	@Secured
	@GET
	@Produces (MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello World!!";
	}
}
