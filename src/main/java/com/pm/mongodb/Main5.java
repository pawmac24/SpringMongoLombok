package com.pm.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.pm.mongodb.model.Book;
import com.pm.mongodb.model.ValueObject;
import configuration.SpringMongoConfig;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

import java.lang.reflect.Field;

/**
 * Created by pmackiewicz on 2016-02-16.
 */
public class Main5 {

    public static final Logger log = LoggerFactory.getLogger(Main5.class);

    public static void main(String[] args) throws Exception {
        new Main5().run();
    }

    public Main5() {
    }

    private void run() throws Exception {

        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection<Document> collection = database.getCollection("books");

        Document doc = new Document( "title", "Understanding JAVA").append("pages", 100);
        collection.insertOne(doc);

        doc = new Document( "title", "Understanding JSON").append("pages", 200);
        collection.insertOne(doc);

        doc = new Document( "title", "Understanding PHP").append("pages", 400);
        collection.insertOne(doc);

        doc = new Document( "title", "Understanding C++").append("pages", 150);
        collection.insertOne(doc);

        doc = new Document( "title","Understanding Python").append("pages", 80);
        collection.insertOne(doc);

        doc = new Document( "title", "Understanding Javascript").append("pages", 500);
        collection.insertOne(doc);

        String map = "function() { "+
                "var category; " +
                "if ( this.pages >= 250 ) "+
                "category = 'Big Books'; " +
                "else " +
                "category = 'Small Books'; "+
                "emit(category, {name: this.name});}";

        String reduce = "function(key, values) { " +
                "var sum = 0; " +
                "values.forEach(function(doc) { " +
                "sum += 1; "+
                "}); " +
                "return {books: sum};} ";

        MongoCursor<Document> iterator = collection.mapReduce(map, reduce).iterator();
        while (iterator.hasNext()){
            Document resDoc = iterator.next();
            System.out.println(resDoc);

        }

        collection.drop();

        mongoClient.close();
    }
}
