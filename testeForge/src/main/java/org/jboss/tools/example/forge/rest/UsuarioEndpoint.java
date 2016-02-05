package org.jboss.tools.example.forge.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.jboss.tools.example.forge.annotations.Seguro;
import org.jboss.tools.example.forge.exceptions.UsuarioExistenteException;
import org.jboss.tools.example.forge.rest.util.TokenUtil;
import org.jboss.tools.example.forge.testeForge.model.TokenVO;
import org.jboss.tools.example.forge.testeForge.model.Usuario;
import org.jboss.tools.example.forge.testeForge.model.UsuarioVO;
import org.jboss.tools.example.forge.testeForge.model.UsuarioVOCreate;

/**
 * 
 */
@Stateless
@Path("/usuarios")
public class UsuarioEndpoint {
	@PersistenceContext(unitName = "testeForge-persistence-unit")
	private EntityManager em;
	
	private TokenUtil tokenUtil = new TokenUtil();

	@POST
	@Path("/create")
	@Consumes("application/json")
	public Response create(UsuarioVOCreate entity) 
	{
		try 
		{
			validarEmailExistente(entity.getEmail());
			Usuario user = new Usuario(entity);
			em.persist(user);
			
			String token = tokenUtil.gerarToken(user);
			
			return Response.ok(new TokenVO(token)).build();
		}
		catch (UsuarioExistenteException e) 
		{
			e.printStackTrace();
		}
		
		return Response.status(Status.CONFLICT).build();
	}
	
	@POST
	@Path("/login")
	@Consumes("application/json")
	public Response login(@Context SecurityContext securityContext,  UsuarioVO entity) 
	{
		try 
		{
			Usuario user = login(entity);
			String token = tokenUtil.gerarToken(user);
			return Response.ok(new TokenVO(token)).build();
        } 
		catch (Exception e) 
		{
			return Response.status(Response.Status.UNAUTHORIZED).build();
        }
	}

	private Usuario login(UsuarioVO entity) throws Exception 
	{
		CriteriaBuilder builder = em.getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);
		criteriaQuery.where(builder.and(builder.equal(root.get("email"), entity.getEmail()), builder.equal(root.get("senha"), entity.getSenha())));
		TypedQuery<Usuario> q = em.createQuery(criteriaQuery);
		Usuario u = q.getSingleResult();
		if(u == null) {
			throw new Exception();
		}
		
		return u;
	}

	private void validarEmailExistente(String login) throws UsuarioExistenteException 
	{
		CriteriaBuilder builder = em.getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Usuario> root = criteriaQuery.from(Usuario.class);
		criteriaQuery.select(builder.count(root));
		criteriaQuery.where(builder.equal(root.get("email"), login));
		TypedQuery<Long> q = em.createQuery(criteriaQuery);
		Long result = q.getSingleResult();
		
		if(result > 0) {
			throw new UsuarioExistenteException();
		}
	}

	@Seguro
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Usuario entity = em.find(Usuario.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@Seguro
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<Usuario> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT u FROM Usuario u WHERE u.id = :entityId ORDER BY u.id",
						Usuario.class);
		findByIdQuery.setParameter("entityId", id);
		Usuario entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@Seguro
	@GET
	@Produces("application/json")
	public List<Usuario> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Usuario> findAllQuery = em
				.createQuery("SELECT DISTINCT u FROM Usuario u ORDER BY u.id",
						Usuario.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Usuario> results = findAllQuery.getResultList();
		return results;
	}

	@Seguro
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Usuario entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(Usuario.class, id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}
