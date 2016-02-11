package org.jboss.tools.example.forge.tokenValidator;

public interface TokenValidator {
	
	String validate(String token) throws TokenNotValidException;
}
