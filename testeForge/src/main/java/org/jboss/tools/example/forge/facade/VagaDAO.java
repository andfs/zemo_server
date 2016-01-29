package org.jboss.tools.example.forge.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.tools.example.forge.testeForge.model.Vaga;

@Stateless
public class VagaDAO {
	
	@PersistenceContext(unitName = "testeForge-persistence-unit")
	private EntityManager em;

	public Long liberarVaga(Vaga vaga, String idUsuario) 
	{
		em.persist(vaga);
		Query query = em.createQuery("select pontos from Usuario u where u.id = :id");
		query.setParameter("id", Long.valueOf(idUsuario));
		Long pontos = (Long) query.getSingleResult();
		if(pontos == null) {
			pontos = 3l;
		}
		
		if(pontos > 10)
		{
			pontos += 5l;
		}
		else if(pontos > 30)
		{
			pontos += 4l;
		}
		else if(pontos > 50)
		{
			pontos += 3l;
		}
		else if(pontos > 100)
		{
			pontos += 2l;
		}
		else {
			pontos++;
		}
		
		query = em.createQuery("update Usuario u set u.pontos = :pontos where u.id = :id");
		query.setParameter("pontos", pontos);
		query.setParameter("id", Long.valueOf(idUsuario));
		query.executeUpdate();
		
		return pontos;
	}

	public Long estacionar(Vaga vaga, String idUsuario) 
	{
		em.persist(vaga);
		Query query = em.createQuery("select pontos from Usuario u where u.id = :id");
		query.setParameter("id", Long.valueOf(idUsuario));
		Long pontos = (Long) query.getSingleResult();
		if(pontos == null) {
			pontos = 0l;
		}
		
		if(pontos > 10)
		{
			pontos += 5l;
		}
		else if(pontos > 30)
		{
			pontos += 4l;
		}
		else if(pontos > 50)
		{
			pontos += 3l;
		}
		else if(pontos > 100)
		{
			pontos += 2l;
		}
		else {
			pontos++;
		}
		
		query = em.createQuery("update Usuario u set u.pontos = :pontos where u.id = :id");
		query.setParameter("pontos", pontos);
		query.setParameter("id", Long.valueOf(idUsuario));
		query.executeUpdate();
		
		return pontos;
	}

}
