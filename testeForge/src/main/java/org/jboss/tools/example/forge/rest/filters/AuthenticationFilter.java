package org.jboss.tools.example.forge.rest.filters;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.tools.example.forge.annotations.Seguro;
import org.jboss.tools.example.forge.rest.securityUtil.CustomSecurity;
import org.jboss.tools.example.forge.rest.util.TokenUtil;

@Seguro
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	private TokenUtil tokenUtil = new TokenUtil();
	
	public void filter(ContainerRequestContext requestContext) throws IOException 
	{
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("A requisição não possui informações sobre a autorização");
        }
		
		String[] login   = authorizationHeader.split(" ");
		String tipoLogin = login[1].trim();
		String token     = login[2].trim();
		
		try 
		{
			String idUser = tokenUtil.validarToken(token, tipoLogin);
			requestContext.setSecurityContext(new CustomSecurity(idUser));
        } 
		catch (Exception e) 
		{
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
	}
}