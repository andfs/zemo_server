package org.jboss.tools.example.forge.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;

import org.bson.Document;
import org.jboss.tools.example.forge.testeForge.model.Estacionamento;
import org.jboss.tools.example.forge.testeForge.model.Vaga;

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

	public List<Estacionamento> buscarTodosEstacionamentosRegiao() 
	{
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("estacionamentos");
		FindIterable<Document> list = usuariosCollection.find();
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

	public List<Vaga> buscarEstacionamentosRegiao(JsonObject bounds) {
		// TODO Auto-generated method stub
		return null;
	}

}
