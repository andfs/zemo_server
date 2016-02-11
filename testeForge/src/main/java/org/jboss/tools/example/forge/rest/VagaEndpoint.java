package org.jboss.tools.example.forge.rest;

import java.io.StringReader;
import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.jboss.tools.example.forge.annotations.Seguro;
import org.jboss.tools.example.forge.facade.VagaDAO;
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
		Long pontos = vagaDAO.liberarVaga(vaga, securityContext.getUserPrincipal().getName());
		
		return Response.ok(new PontosJson(pontos)).build();
	}
	
	@POST
	@Path("/estacionar")
	@Consumes("application/json")
	public Long estacionar(@Context SecurityContext securityContext, VagaVO entity) 
	{
		Vaga vaga = new Vaga(entity.getLatitude(), entity.getLongitude());
		Long pontos = vagaDAO.estacionar(vaga, securityContext.getUserPrincipal().getName());
		return pontos;
	}

	@GET
	@Produces("application/json")
	public List<Vaga> listAll(@Context UriInfo info) 
	{
		JsonReader reader = Json.createReader(new StringReader(info.getQueryParameters().get("params").get(0)));
		JsonObject object = reader.readObject();
		JsonObject bounds = object.getJsonObject("bounds");
        reader.close();
        //TODO trazer estacionamentos tambem
		return vagaDAO.buscarTodasVagasRegiao(bounds);
	}
}
