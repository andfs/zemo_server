package org.jboss.tools.example.forge.tokenValidator;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.tools.example.forge.testeForge.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenValidatorProprio implements TokenValidator{

	private String issuer = "zemoIssuer";
	private String aud = "zemoAud";
	
	@SuppressWarnings("rawtypes")
	@Override
	public Long validate(String token, EntityManager em) throws TokenNotValidException 
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
				Query q = em.createQuery("from Usuario u where u.id = :id and u.senha = :senha and u.email = :email");
				q.setParameter("id", Long.valueOf(claims.get(PayloadBody.ID.name()).toString()));
				q.setParameter("email", claims.get(PayloadBody.EMAIL.name()));
				q.setParameter("senha", claims.get(PayloadBody.SENHA.name()));
				Usuario result = (Usuario) q.getSingleResult();
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
