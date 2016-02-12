package org.jboss.tools.example.forge.facade;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Stateless
public class PontosDAO {

	@Inject
	private MongoDatabase mongoDatabase;

	public Long recuperarPontos(String idUsuario) 
	{
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document query = new Document("_id", new ObjectId(idUsuario));
		Long pontos = usuariosCollection.find(query).iterator().next().getLong("pontos");
		
		return pontos;
	}
	
	
}
