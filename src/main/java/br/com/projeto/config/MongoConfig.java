package br.com.projeto.config;

import com.mongodb.client.*;

public final class MongoConfig {
  private static final String URI = System.getenv().getOrDefault("MONGODB_URI", "mongodb://localhost:27017");
  private static final MongoClient CLIENT = MongoClients.create(URI);
  private static final MongoDatabase DB = CLIENT.getDatabase("projeto_faculdade");

  private MongoConfig() {
  }

  public static MongoDatabase database() {
    return DB;
  }

  public static void close() {
    CLIENT.close();
  }
}
