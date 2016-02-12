package org.jboss.tools.example.forge.facade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;

import org.bson.Document;
import org.jboss.tools.example.forge.testeForge.model.Estacionamento;
import org.jboss.tools.example.forge.testeForge.model.Vaga;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Stateless
public class EstacionamentoDAO {
	
	@Inject
	private MongoDatabase mongoDatabase;

	public void persist(Estacionamento entity) 
	{
		
	}

	public void remove(Estacionamento entity) {
		MongoCollection<Document> estacionamentosCollection = mongoDatabase.getCollection("estacionamentos");
		Document query = new Document();
		query.put("_id", entity.getId());
		estacionamentosCollection.deleteOne(query);
	}

	public Estacionamento find(String id) 
	{
		Document query = new Document();
		query.put("_id", id);
		
		MongoCollection<Document> estacionamentosCollection = mongoDatabase.getCollection("estacionamentos");
		Document estacionamento = estacionamentosCollection.find(query).first();
		if(estacionamento == null) {
			return null;
		}
		
		Estacionamento result = new Estacionamento();
		result.setId(estacionamento.getString("_id"));
		result.setNome(estacionamento.getString("nome"));
		result.setN(estacionamento.getString("numero"));
		result.setEndereco(estacionamento.getString("endereco"));
		result.setLatitude(estacionamento.getLong("localizacao.latitude"));
		result.setLongitude(estacionamento.getLong("localizacao.longitude"));
		result.setValorFracao(estacionamento.getDouble("valorFracao"));
		result.setValorHora(estacionamento.getDouble("valorHora"));
		
		return result;
	}

	public List<Estacionamento> buscarTodosEstacionamentos(JsonObject posicaoAtual) 
	{
		MongoCollection<Document> estacionamentosCollection = mongoDatabase.getCollection("estacionamentos");
		
		LinkedList<double[]> geo = new LinkedList<>();
		geo.addLast(new double[]{Double.valueOf(posicaoAtual.get("latitude").toString()), Double.valueOf(posicaoAtual.get("longitude").toString())});
		
		
		Document near = new Document();
		near.append("$near", geo);
		Document query = new Document();
		query.append("localizacao", near);
		
		FindIterable<Document> list = estacionamentosCollection.find(query);
		List<Estacionamento> result = new ArrayList<>(); 
		while(list.iterator().hasNext())
		{
			Document doc = list.iterator().next();
			Estacionamento estacionamento = new Estacionamento();
			estacionamento.setId(doc.getString("_id"));
			estacionamento.setNome(doc.getString("nome"));
			estacionamento.setN(doc.getString("numero"));
			estacionamento.setEndereco(doc.getString("endereco"));
			estacionamento.setLatitude(doc.getLong("localizacao.latitude"));
			estacionamento.setLongitude(doc.getLong("localizacao.longitude"));
			estacionamento.setValorFracao(doc.getDouble("valorFracao"));
			estacionamento.setValorHora(doc.getDouble("valorHora"));
			
			result.add(estacionamento);
		}
		return result;
	}

	public List<Vaga> buscarEstacionamentosRegiao(JsonObject bounds) 
	{
		MongoCollection<Document> estacionamentosCollection = mongoDatabase.getCollection("estacionamentos");
		
		LinkedList<double[]> geo = new LinkedList<>();
		geo.addLast(new double[]{Double.valueOf(((JsonObject)bounds.get("southwest")).get("lat").toString()), Double.valueOf(((JsonObject)bounds.get("southwest")).get("lng").toString())});
		geo.addLast(new double[]{Double.valueOf(((JsonObject)bounds.get("northeast")).get("lat").toString()), Double.valueOf(((JsonObject)bounds.get("northeast")).get("lng").toString())});
		
	    BasicDBObject geometry = new BasicDBObject();
	    geometry.append("$box", geo);
		
		BasicDBObject query = new BasicDBObject("localizacao", new BasicDBObject("$geoWithin", geometry));
		
		FindIterable<Document> result = estacionamentosCollection.find(query);
		List<Vaga> vagas = new ArrayList<>();
		result.forEach(new Block<Document>() 
		{
			public void apply(Document doc) 
			{
				Vaga vaga = new Vaga();
				vaga.setId(doc.getObjectId("_id").toString());
				vaga.setLatitude(Double.valueOf(((Document)doc.get("localizacao")).get("latitude").toString()));
				vaga.setLongitude(Double.valueOf(((Document)doc.get("localizacao")).get("longitude").toString()));
				vaga.setIsVaga(false);
				vaga.setInfo(doc.getString("nome") + " - " + doc.getString("valorHora")+"\\h" + " - " + doc.getString("valorFracao") + " fração");
				
				vagas.add(vaga);
			}
		});
		
		return vagas;
	}

}
