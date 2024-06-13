package com.mokshesh.ontology;

import com.mokshesh.ontology.model.*;
import com.mokshesh.ontology.util.Utility;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;



public class ExportObjectTypeDetails {

  public static void main(String[] args) {
    String host = "localhost";
    String port = "27017";
    String username = "root";
    String password = "root";

    String database_name = "cipla";

    // Connection string to MongoDB server
    String uri = String.format("mongodb://%s:%s@%s:%s/?retryWrites=true&loadBalanced=false&serverSelectionTimeoutMS=5000&connectTimeoutMS=10000&authSource=admin&authMechanism=SCRAM-SHA-256", username, password, host, port);

    MongoClient mongoClient = MongoClients.create(uri);

    // Connect to the database
    MongoDatabase database = mongoClient.getDatabase(database_name);

    // Get a collection from the database
    MongoCollection<Document> objectTypeCollection = database.getCollection("objectTypes");
    FindIterable<Document> iterable = objectTypeCollection.find();
    List<ObjectType> objectTypes = new ArrayList<>();
    for (Document document : iterable) {
      ObjectType objectType = Utility.convertDocumentToObjectType(document);
      objectTypes.add(objectType);
    }
    Utility.export("object_types", objectTypes);

    // Close the MongoClient
    mongoClient.close();
  }



}
