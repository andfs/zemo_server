package org.jboss.tools.example.forge.tokenValidator;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;

import javax.enterprise.inject.spi.CDI;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.jboss.tools.example.forge.facade.UsuarioDAO;
import org.jboss.tools.example.forge.testeForge.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenValidatorProprio implements TokenValidator{

	private String issuer = "zemoIssuer";
	private String aud = "zemoAud";
	
	private UsuarioDAO usuarioDAO = CDI.current().select(UsuarioDAO.class).get();
	
	@SuppressWarnings("rawtypes")
	@Override
	public String validate(String token) throws TokenNotValidException 
	{
		try
		{
			String k = ResourceBundle.getBundle("keys").getString("public").split(" ")[1];
			byte[] bytes = Base64.getEncoder().encode(k.getBytes());
			
			Jwt jwt = Jwts.parser().setSigningKey(bytes).parse(token);
			
			if(jwt.getBody() == null) {
				throw new TokenNotValidException();
			}
			Claims claims = (Claims) jwt.getBody();
			
			if(claims.get(PayloadBody.AUDIENCE.name()).equals(aud) && claims.get(PayloadBody.ISSUER.name()).equals(issuer))
			{
				Usuario result = usuarioDAO.buscarUsuario(claims.get(PayloadBody.ID.name()).toString(), claims.get(PayloadBody.EMAIL.name()).toString(), claims.get(PayloadBody.SENHA.name()).toString());
				if(result == null || result.getId() == null) {
					throw new TokenNotValidException();
				}
				
				return result.getId();
			}
			else {
				throw new TokenNotValidException();
			}
			
		}
		catch(Exception e)
		{
			throw new TokenNotValidException();
		}
	}
	
	public String gerarToken(Usuario user)
	{
		try 
		{
			String k = ResourceBundle.getBundle("keys").getString("public").split(" ")[1];
			byte[] bytes = Base64.getEncoder().encode(k.getBytes());
			JwtBuilder jwt = Jwts.builder();
			String payload = gerarPayload(user);
			String s = jwt.setPayload(payload).signWith(SignatureAlgorithm.HS512, bytes).compact();
			return s;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private String gerarPayload(Usuario user) 
	{
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		objectBuilder.add(PayloadBody.ID.name(), user.getId())
					 .add(PayloadBody.NOME.name(), user.getNome())
					 .add(PayloadBody.EMAIL.name(), user.getEmail())
					 .add(PayloadBody.SENHA.name(), user.getSenha())
					 .add(PayloadBody.SUBJECT.name(), user.getId()+""+new Date().getTime()+"")
					 .add(PayloadBody.ISSUER.name(), issuer)
					 .add(PayloadBody.AUDIENCE.name(), aud)
					 .add(PayloadBody.ISSUEDAT.name(), new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		
		return objectBuilder.build().toString();
	}
	
	private enum PayloadBody
	{
		ID,
		NOME,
		EMAIL,
		SENHA,
		SUBJECT,
		ISSUER,
		AUDIENCE,
		ISSUEDAT;
	}
}
