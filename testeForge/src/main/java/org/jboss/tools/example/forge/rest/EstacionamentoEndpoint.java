package org.jboss.tools.example.forge.rest;

import java.io.StringReader;
import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.jboss.tools.example.forge.annotations.Seguro;
import org.jboss.tools.example.forge.facade.EstacionamentoDAO;
import org.jboss.tools.example.forge.testeForge.model.Estacionamento;

/**
 * 
 */

@Path("/estacionamentos")
public class EstacionamentoEndpoint {

	@Inject
	private EstacionamentoDAO estacionamentoDAO;

	@POST
	@Consumes("application/json")
	public Response create(Estacionamento entity) {
		//TODO
		return Response.ok().build();
	}
	
	@Seguro
	@POST
	@Path("/obterPromocao")
	@Consumes("application/json")
	public Response obterPromocao(@Context SecurityContext securityContext, Estacionamento entity) {
		//TODO
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") String id) {
		Estacionamento entity = estacionamentoDAO.find(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		estacionamentoDAO.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") String id) 
	{
		Estacionamento entity = estacionamentoDAO.find(id);
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Estacionamento> listAll(@Context UriInfo info) 
	{
		JsonReader reader = Json.createReader(new StringReader(info.getQueryParameters().get("position").get(0)));
		JsonObject position = reader.readObject();
        reader.close();
		return estacionamentoDAO.buscarTodosEstacionamentos(position);
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") String id, Estacionamento entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		
		if (estacionamentoDAO.find(id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		//TODO
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}
