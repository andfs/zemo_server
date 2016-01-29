package org.jboss.tools.example.forge.tokenValidator;

import javax.persistence.EntityManager;

public interface TokenValidator {
	
	Long validate(String token, EntityManager em) throws TokenNotValidException;
}
