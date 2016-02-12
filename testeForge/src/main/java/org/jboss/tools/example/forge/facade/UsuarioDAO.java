package org.jboss.tools.example.forge.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.jboss.tools.example.forge.testeForge.model.Usuario;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Stateless
public class UsuarioDAO {
	
	@Inject
	private MongoDatabase mongoDatabase;

	public void persist(Usuario user) {
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document usuario = new Document();
		usuario.append("email", user.getEmail()).append("senha", user.getSenha()).append("nome", user.getNome());
		
		usuariosCollection.insertOne(usuario);
		user.setId(usuario.getObjectId("_id").toString());
	}

	public Usuario buscarUsuario(String _id, String email, String senha) 
	{
		Document query = new Document();
		query.append("_id", new ObjectId(_id)).append("email", email).append("senha", senha);
		
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document user = usuariosCollection.find(query).first();
		if(user == null) {
			return null;
		}
		
		Usuario result = new Usuario();
		result.setId(user.getObjectId("_id").toHexString());
		
		return result;
	}
	
	public Usuario buscarUsuario(String email, String senha) 
	{
		Document query = new Document();
		query.append("email", email).append("senha", senha);
		
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document user = usuariosCollection.find(query).first();
		if(user == null) {
			return null;
		}
		
		Usuario result = new Usuario();
		result.setId(user.getObjectId("_id").toString());
		result.setNome(user.getString("nome"));
		result.setPontos(user.getLong("pontos"));
		result.setEmail(user.getString("email"));
		result.setSenha(user.getString("senha"));
		
		return result;
	}

	public void delete(String id) {
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document query = new Document();
		query.put("_id", id);
		usuariosCollection.deleteOne(query);
	}

	public Usuario findById(String id) 
	{
		Document query = new Document();
		query.put("_id", id);
		
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document user = usuariosCollection.find(query).first();
		if(user == null) {
			return null;
		}
		
		Usuario result = new Usuario();
		result.setId(user.getString("_id"));
		result.setNome(user.getString("nome"));
		result.setPontos(user.getLong("pontos"));
		result.setEmail(user.getString("email"));
		result.setToken(user.getString("token"));
		
		return result;
	}

	public List<Usuario> listAll(Integer startPosition, Integer maxResult) 
	{
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		FindIterable<Document> list = usuariosCollection.find().skip(startPosition).limit(maxResult);
		List<Usuario> result = new ArrayList<>(); 
		while(list.iterator().hasNext())
		{
			Document doc = list.iterator().next();
			Usuario usuario = new Usuario();
			usuario.setEmail(doc.getString("email"));
			usuario.setId(doc.getString("_id"));
			usuario.setNome(doc.getString("nome"));
			usuario.setPontos(doc.getLong("pontos"));
			usuario.setToken(doc.getString("token"));
			
			result.add(usuario);
		}
		return result;
	}

	public Long countUserByEmail(String login) 
	{
		MongoCollection<Document> usuariosCollection = mongoDatabase.getCollection("usuarios");
		Document query = new Document();
		query.put("email", login);
		
		Long result = usuariosCollection.count(query);
		return result;
	}
}
