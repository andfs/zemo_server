package org.jboss.tools.example.forge.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.bson.Document;
import org.jboss.tools.example.forge.testeForge.model.Vaga;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Stateless
public class VagaDAO {
	
	@PersistenceContext(unitName = "testeForge-persistence-unit")
	private EntityManager em;
	
	@Inject
	private MongoDatabase mongoDatabase;

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

	public List<Vaga> buscarTodasVagasRegiao(JsonObject centre, JsonObject bounds, Integer zoom, Double boundingRadius) 
	{
		MongoCollection<Document> vagasCollection = mongoDatabase.getCollection("vagas");
		BasicDBList geoCoord = new BasicDBList();
		geoCoord.add(((JsonObject)bounds.get("northeast")).get("longitude"));
		geoCoord.add(((JsonObject)bounds.get("northeast")).get("latitude"));
		geoCoord.add(((JsonObject)bounds.get("southwest")).get("longitude"));
		geoCoord.add(((JsonObject)bounds.get("southwest")).get("latitude"));
		
		BasicDBList geoParams = new BasicDBList();
	    geoParams.add(geoCoord);
	    geoParams.add(boundingRadius);
		
		BasicDBObject query = new BasicDBObject("localizacao", new BasicDBObject("$geoWithin", new BasicDBObject("$center", geoParams)));
		
		FindIterable<Document> result = vagasCollection.find(Filters.and(query, Filters.eq("statusVaga", 0)));
		
		result.forEach(new Block<Document>() 
		{
			public void apply(Document doc) 
			{
								
			}
		});
		
		return null;
	}

}
