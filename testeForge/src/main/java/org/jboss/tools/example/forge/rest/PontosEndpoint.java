package org.jboss.tools.example.forge.rest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.tools.example.forge.annotations.Seguro;
import org.jboss.tools.example.forge.testeForge.model.PontosJson;

@Stateless
@Path("/pontos")
public class PontosEndpoint {
	
	@PersistenceContext(unitName = "testeForge-persistence-unit")
	private EntityManager em;

	@Seguro
	@GET
	@Produces("application/json")
	@Path("pt")
	public Response getPontos(@Context SecurityContext securityContext)
	{
		Query query = em.createQuery("select u.pontos from Usuario u where u.id = :id");
		query.setParameter("id", Long.valueOf(securityContext.getUserPrincipal().getName()));
		Long pontos = (Long) query.getSingleResult();
		if(pontos == null) {
			pontos = 0l;
		}
		return Response.ok(new PontosJson(pontos)).build();
	}
}
