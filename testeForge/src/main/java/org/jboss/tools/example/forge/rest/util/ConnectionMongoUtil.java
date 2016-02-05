package org.jboss.tools.example.forge.rest.util;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@Startup
@Singleton
public class ConnectionMongoUtil 
{
	
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	
	private static final Logger logger = Logger.getLogger(ConnectionMongoUtil.class.getName());	
	
	@PostConstruct
	public void init()
	{
		logger.info("Conectando ao mongo...");
		MongoClientOptions m = MongoClientOptions.builder().build();
		mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), m);
		mongoDatabase = mongoClient.getDatabase("local");
		logger.info("Conectado ao mongo!");
	}
	
	@PreDestroy
	public void destroy() {
		mongoClient.close();
	}

	@Produces
	public MongoDatabase getMongoClient() {
		return mongoDatabase;
	}
}