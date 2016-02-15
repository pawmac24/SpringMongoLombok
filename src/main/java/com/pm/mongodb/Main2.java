package com.pm.mongodb;

import com.pm.mongodb.model.Address;
import com.pm.mongodb.model.Answer;
import com.pm.mongodb.model.Person;
import configuration.SpringMongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.MongoOperations;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class Main2 {

    public static final Logger log = LoggerFactory.getLogger(Main2.class);

    public static void main(String[] args) throws Exception {
        new Main2().run();
    }

    public Main2() {
    }

    private void run() throws Exception {

        try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class)) {
            MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

            try {
                List<Answer> answerList = new ArrayList<>();
                answerList.add(new Answer("Q1", "A", true, 10));
                answerList.add(new Answer("Q2", "B", true, 4));
                answerList.add(new Answer("Q3", "C", false, 5));
                answerList.add(new Answer("Q4", "C", true, 2));
                answerList.add(new Answer("Q5", "B", false, 4));
                answerList.add(new Answer("Q6", "A", false, 5));
                Address address = new Address("88-888", "Gdansk", "Piastowska", "21");
                Person person = new Person("Andy", "Marrow", 17, address, answerList, LocalDate.of(1999, Month.JANUARY, 1));
                mongoOperations.insert(person);

                Person tmp = mongoOperations.findOne(query(where("id").is(person.getId())), Person.class);

                person.setFirstname("John");
                mongoOperations.save(person);
                mongoOperations.save(tmp); // throws OptimisticLockingFailureException
            }
            catch(OptimisticLockingFailureException oe){
                log.info(oe.getMessage() + "");
            }
            finally {
                // drop collection
                mongoOperations.dropCollection(Person.class);
            }
        }
    }
}
