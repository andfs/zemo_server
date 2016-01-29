package org.jboss.tools.example.forge.tokenValidator;

import org.jboss.tools.example.forge.testeForge.model.Usuario;

public class TokenValidatorFactory 
{
	private TokenValidatorFactory(){}
	
	public static TokenValidator createTokenValidator(String tipoLogin)
	{
		TokenValidator tokenValidator = null;
		switch (tipoLogin) 
		{
			case "google":
				tokenValidator = new TokenValidatorGoogle();
				break;
	
			case "facebook":
				tokenValidator = new TokenValidatorFacebook();
				break;
				
			case "proprio":
				tokenValidator = new TokenValidatorProprio();
				break;
		}
		
		return tokenValidator;
	}
	
	public static String createToken(Usuario usuario) {
		return new TokenValidatorProprio().gerarToken(usuario);
	}
}
