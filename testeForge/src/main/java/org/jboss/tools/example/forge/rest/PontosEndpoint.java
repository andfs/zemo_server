package org.jboss.tools.example.forge.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.tools.example.forge.annotations.Seguro;
import org.jboss.tools.example.forge.facade.PontosDAO;
import org.jboss.tools.example.forge.testeForge.model.PontosJson;


@Path("/pontos")
public class PontosEndpoint {
	
	@Inject
	private PontosDAO pontosDAO;
	
	@Seguro
	@GET
	@Produces("application/json")
	@Path("pt")
	public Response getPontos(@Context SecurityContext securityContext)
	{
		Long pontos = pontosDAO.recuperarPontos(securityContext.getUserPrincipal().getName());
		if(pontos == null) {
			pontos = 0l;
		}
		return Response.ok(new PontosJson(pontos)).build();
	}
}
