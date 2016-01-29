package org.jboss.tools.example.forge.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.tools.example.forge.annotations.Seguro;
import org.jboss.tools.example.forge.facade.VagaDAO;
import org.jboss.tools.example.forge.testeForge.model.EnumStatusVaga;
import org.jboss.tools.example.forge.testeForge.model.PontosJson;
import org.jboss.tools.example.forge.testeForge.model.Vaga;
import org.jboss.tools.example.forge.testeForge.model.VagaVO;

/**
 * 
 */
@Path("/vagas")
public class VagaEndpoint {
	
	@Inject
	private VagaDAO vagaDAO;
	
	@Seguro
	@POST
	@Path("/liberarVaga")
	@Consumes("application/json")
	public Response liberarVaga(@Context SecurityContext securityContext, VagaVO entity) 
	{
		Vaga vaga = new Vaga(entity.getLatitude(), entity.getLongitude());
		vaga.setStatusVaga(EnumStatusVaga.DESOCUPADA);
		
		Long pontos = vagaDAO.liberarVaga(vaga, securityContext.getUserPrincipal().getName());
		
		return Response.ok(new PontosJson(pontos)).build();
	}
	
	@POST
	@Path("/estacionar")
	@Consumes("application/json")
	public Long estacionar(@Context SecurityContext securityContext, VagaVO entity) 
	{
		Vaga vaga = new Vaga(entity.getLatitude(), entity.getLongitude());
		vaga.setStatusVaga(EnumStatusVaga.OCUPADA);
		Long pontos = vagaDAO.estacionar(vaga, securityContext.getUserPrincipal().getName());
		return pontos;
	}

	@GET
	@Produces("application/json")
	public List<Vaga> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) 
	{
//		TypedQuery<Vaga> findAllQuery = em.createQuery(
//				"SELECT DISTINCT v FROM Vaga v ORDER BY v.id", Vaga.class);
//		if (startPosition != null) {
//			findAllQuery.setFirstResult(startPosition);
//		}
//		if (maxResult != null) {
//			findAllQuery.setMaxResults(maxResult);
//		}
//		final List<Vaga> results = findAllQuery.getResultList();
//		return results;
		
		return null;
	}
}
