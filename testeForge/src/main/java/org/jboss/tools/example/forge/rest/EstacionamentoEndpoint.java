package org.jboss.tools.example.forge.rest;

import java.io.StringReader;
import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

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
		estacionamentoDAO.persist(entity);
		

		return Response.created(
				UriBuilder.fromResource(EstacionamentoEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
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
	public Response findById(@PathParam("id") Long id) {
//		TypedQuery<Estacionamento> findByIdQuery = em
//				.createQuery(
//						"SELECT DISTINCT e FROM Estacionamento e WHERE e.id = :entityId ORDER BY e.id",
//						Estacionamento.class);
//		findByIdQuery.setParameter("entityId", id);
//		Estacionamento entity;
//		try {
//			entity = findByIdQuery.getSingleResult();
//		} catch (NoResultException nre) {
//			entity = null;
//		}
//		if (entity == null) {
//			return Response.status(Status.NOT_FOUND).build();
//		}
		return Response.ok(/*entity*/).build();
	}

	@GET
	@Produces("application/json")
	public List<Estacionamento> listAll(@Context UriInfo info) 
	{
		JsonReader reader = Json.createReader(new StringReader(info.getQueryParameters().get("params").get(0)));
		JsonObject object = reader.readObject();
		JsonObject bounds = object.getJsonObject("bounds");
        reader.close();
        
		return estacionamentoDAO.buscarTodosEstacionamentosRegiao(bounds);
	}

//	@PUT
//	@Path("/{id:[0-9][0-9]*}")
//	@Consumes("application/json")
//	public Response update(@PathParam("id") Long id, Estacionamento entity) {
//		if (entity == null) {
//			return Response.status(Status.BAD_REQUEST).build();
//		}
//		if (id == null) {
//			return Response.status(Status.BAD_REQUEST).build();
//		}
//		if (!id.equals(entity.getId())) {
//			return Response.status(Status.CONFLICT).entity(entity).build();
//		}
//		if (em.find(Estacionamento.class, id) == null) {
//			return Response.status(Status.NOT_FOUND).build();
//		}
//		try {
//			entity = em.merge(entity);
//		} catch (OptimisticLockException e) {
//			return Response.status(Response.Status.CONFLICT)
//					.entity(e.getEntity()).build();
//		}
//
//		return Response.noContent().build();
//	}
}
