package org.jboss.tools.example.forge.rest.securityUtil;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class CustomSecurity implements SecurityContext {

	private Long idUser;
	
	public CustomSecurity(Long idUser) {
		this.idUser = idUser;
	}
	
	@Override
	public boolean isUserInRole(String arg0) {
		return true;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		return new Principal() {

			@Override
			public String getName() {
				return idUser.toString();
			}
		};
	}

	@Override
	public String getAuthenticationScheme() {
		return null;
	}
}
