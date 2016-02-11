package org.jboss.tools.example.forge.rest.util;

import org.jboss.tools.example.forge.testeForge.model.Usuario;
import org.jboss.tools.example.forge.tokenValidator.TokenValidator;
import org.jboss.tools.example.forge.tokenValidator.TokenValidatorFactory;

public class TokenUtil 
{
	public String gerarToken(Usuario user) {
		return TokenValidatorFactory.createToken(user);
	}

	public String validarToken(String token, String tipoLogin) throws Exception
	{
		TokenValidator tokenValidator = TokenValidatorFactory.createTokenValidator(tipoLogin);
		return tokenValidator.validate(token);
	}
}
