package org.jboss.tools.example.forge.rest.util;

import javax.persistence.EntityManager;

import org.jboss.tools.example.forge.testeForge.model.Usuario;
import org.jboss.tools.example.forge.tokenValidator.TokenValidator;
import org.jboss.tools.example.forge.tokenValidator.TokenValidatorFactory;

public class TokenUtil 
{
	public String gerarToken(Usuario user) {
		return TokenValidatorFactory.createToken(user);
	}

	public Long validarToken(String token, String tipoLogin, EntityManager em) throws Exception
	{
		TokenValidator tokenValidator = TokenValidatorFactory.createTokenValidator(tipoLogin);
		return tokenValidator.validate(token, em);
	}
}
