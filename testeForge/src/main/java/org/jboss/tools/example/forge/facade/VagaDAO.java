package org.jboss.tools.example.forge.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.bson.Document;
import org.jboss.tools.example.forge.testeForge.model.EnumStatusVaga;
import org.jboss.tools.example.forge.testeForge.model.EnumTipoVaga;
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
		vagasCollection.find();
		BasicDBList geoCoord = new BasicDBList();
		geoCoord.add(Double.valueOf(centre.get("lng").toString()));
		geoCoord.add(Double.valueOf(centre.get("lat").toString()));
//		geoCoord.add(Double.valueOf(((JsonObject)bounds.get("southwest")).get("lng").toString()));
//		geoCoord.add(Double.valueOf(((JsonObject)bounds.get("southwest")).get("lat").toString()));
		
		BasicDBList geoParams = new BasicDBList();
	    geoParams.add(geoCoord);
	    geoParams.add(boundingRadius);
		
		BasicDBObject query = new BasicDBObject("localizacao", new BasicDBObject("$geoWithin", new BasicDBObject("$centerSphere", geoParams)));
		
		FindIterable<Document> result = vagasCollection.find(Filters.and(query, Filters.eq("statusVaga", 0)));
		List<Vaga> vagas = new ArrayList<>();
		result.forEach(new Block<Document>() 
		{
			public void apply(Document doc) 
			{
				Vaga vaga = new Vaga();
				vaga.setId(doc.getObjectId("_id").toString());
				vaga.setLatitude(Double.valueOf(((Document)doc.get("localizacao")).get("latitude").toString()));
				vaga.setLongitude(Double.valueOf(((Document)doc.get("localizacao")).get("longitude").toString()));
				vaga.setStatusVaga(EnumStatusVaga.getEnum(doc.getDouble("statusVaga").intValue()));
				vaga.setTipoVaga(EnumTipoVaga.getEnum(doc.getDouble("tipoVaga").intValue()));
				vaga.setIsVaga(true);
				vaga.setInfo(vaga.getTipoVaga().getInfo());
				
				vagas.add(vaga);
			}
		});
		
		return vagas;
	}

}
