package org.jboss.tools.example.forge.tokenValidator;

import javax.persistence.EntityManager;

class TokenValidatorGoogle implements TokenValidator{

	@Override
	public Long validate(String token, EntityManager em) throws TokenNotValidException {
		// TODO Auto-generated method stub
		return null;
	}
}
