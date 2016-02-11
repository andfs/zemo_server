package org.jboss.tools.example.forge.facade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.jboss.tools.example.forge.testeForge.model.EnumStatusVaga;
import org.jboss.tools.example.forge.testeForge.model.EnumTipoVaga;
import org.jboss.tools.example.forge.testeForge.model.Vaga;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Stateless
public class VagaDAO {
	
	@Inject
	private MongoDatabase mongoDatabase;

	public Long liberarVaga(Vaga vaga, String idUsuario) 
	{
		updateInsertVaga(vaga, EnumStatusVaga.DESOCUPADA.ordinal());
		
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document queryUsuario = new Document();
		queryUsuario.put("_id", new ObjectId(idUsuario));
		FindIterable<Document> result = usuariosCollection.find(queryUsuario);
		Long pontos = 0l;
		if(result.iterator().hasNext())
		{
			Document usuario = result.iterator().next();
			pontos = usuario.getLong("pontos");
			
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
			
			usuario.put("pontos", pontos);
			
			usuariosCollection.updateOne(queryUsuario, usuario);
		}
		
		return pontos;
	}

	private void updateInsertVaga(Vaga vaga, int statusVaga) {
		MongoCollection<Document> vagasCollection = mongoDatabase.getCollection("vagas");
		Document query = new Document();
		query.put("localizacao.latitude", vaga.getLatitude());
		query.put("localizacao.longitude", vaga.getLatitude());
		FindIterable<Document> result = vagasCollection.find(query);
		boolean achou = false;
		while(result.iterator().hasNext())
		{
			achou = true;
			Document doc = result.iterator().next();
			doc.put("statusVaga", statusVaga);
			vagasCollection.updateOne(query, doc);
		}
		if(!achou)
		{
			Document localizacao = new Document();
			localizacao.put("latitude", vaga.getLatitude());
			localizacao.put("longitude", vaga.getLatitude());
			Document vagaDoc = new Document();
			vagaDoc.put("localizacao", localizacao);
			vagaDoc.put("statusVaga", statusVaga);
			vagaDoc.put("isVaga", true);
			vagaDoc.put("tipoVaga", vaga.getTipoVaga().ordinal());
			
			vagasCollection.insertOne(vagaDoc);
		}
	}

	public Long estacionar(Vaga vaga, String idUsuario) 
	{
		updateInsertVaga(vaga, EnumStatusVaga.OCUPADA.ordinal());
		
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document queryUsuario = new Document();
		queryUsuario.put("_id", new ObjectId(idUsuario));
		FindIterable<Document> result = usuariosCollection.find(queryUsuario);
		Long pontos = 0l;
		if(result.iterator().hasNext())
		{
			Document usuario = result.iterator().next();
			pontos = usuario.getLong("pontos");
			
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
			
			usuario.put("pontos", pontos);
			
			usuariosCollection.updateOne(queryUsuario, usuario);
		}
		
		return pontos;
	}

	public List<Vaga> buscarTodasVagasRegiao(JsonObject bounds) 
	{
		MongoCollection<Document> vagasCollection = mongoDatabase.getCollection("vagas");
		vagasCollection.find();
		LinkedList<double[]> geo = new LinkedList<>();
		geo.addLast(new double[]{Double.valueOf(((JsonObject)bounds.get("southwest")).get("lat").toString()), Double.valueOf(((JsonObject)bounds.get("southwest")).get("lng").toString())});
		geo.addLast(new double[]{Double.valueOf(((JsonObject)bounds.get("northeast")).get("lat").toString()), Double.valueOf(((JsonObject)bounds.get("northeast")).get("lng").toString())});
		
	    BasicDBObject geometry = new BasicDBObject();
	    geometry.append("$box", geo);
		
		BasicDBObject query = new BasicDBObject("localizacao", new BasicDBObject("$geoWithin", geometry));
		
		FindIterable<Document> result = vagasCollection.find(query);
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
