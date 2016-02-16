package com.pm.mongodb;

import com.pm.mongodb.model.Book;
import com.pm.mongodb.model.ValueObject;
import configuration.SpringMongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

/**
 * Created by pmackiewicz on 2016-02-16.
 */
public class Main4 {

    public static final Logger log = LoggerFactory.getLogger(Main4.class);

    public static void main(String[] args) throws Exception {
        new Main4().run();
    }

    public Main4() {
    }

    private void run() throws Exception {

        try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class)) {
            MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

            Book book = new Book("Understanding JAVA", 100);
            mongoOperations.insert(book);

            book = new Book("Understanding JSON", 200);
            mongoOperations.insert(book);

            book = new Book("Understanding PHP", 400);
            mongoOperations.insert(book);

            book = new Book("Understanding C++", 150);
            mongoOperations.insert(book);

            book = new Book("Understanding Python", 80);
            mongoOperations.insert(book);

            book = new Book("Understanding Javascript", 500);
            mongoOperations.insert(book);

            MapReduceResults<ValueObject> results = mongoOperations.mapReduce("books", "classpath:map.js", "classpath:reduce.js", ValueObject.class);
            for (ValueObject valueObject : results) {
                System.out.println(valueObject);
            }

            mongoOperations.dropCollection(Book.class);
        }
    }
}
