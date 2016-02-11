package org.jboss.tools.example.forge.rest.securityUtil;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class CustomSecurity implements SecurityContext {

	private String idUser;
	
	public CustomSecurity(String idUser) {
		this.idUser = idUser;
	}
	
	public boolean isUserInRole(String arg0) {
		return true;
	}

	public boolean isSecure() {
		return false;
	}

	public Principal getUserPrincipal() {
		return new Principal() {

			public String getName() {
				return idUser.toString();
			}
		};
	}

	public String getAuthenticationScheme() {
		return null;
	}
}
