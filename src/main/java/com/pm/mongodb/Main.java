package com.pm.mongodb;

import com.mongodb.WriteResult;
import com.pm.mongodb.model.Address;
import com.pm.mongodb.model.Answer;
import com.pm.mongodb.model.Person;
import configuration.SpringMongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class Main {

    public static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    public Main() {
    }

    private void run() throws Exception {

        try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class)) {
            MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

            List<Answer> answerList = new ArrayList<>();
            answerList.add(new Answer("Q1", "A", true, 10));
            answerList.add(new Answer("Q2", "B", true, 4));
            answerList.add(new Answer("Q3", "C", false, 5));
            answerList.add(new Answer("Q4", "C", true, 2));
            answerList.add(new Answer("Q5", "B", false, 4));
            answerList.add(new Answer("Q6", "A", false, 5));
            Address address = new Address("88-888", "Gdansk", "Piastowska", "21");
            System.out.print(address.getCity());
            Person person = new Person("Joe", "Maclean", 34, address, answerList, LocalDate.of(1982, Month.JANUARY, 1));
            mongoOperations.save(person);
            log.info("(1) inserted = " + person);

            answerList = new ArrayList<>();
            answerList.add(new Answer("Q1", "A", true, 10));
            answerList.add(new Answer("Q2", "C", false, 4));
            answerList.add(new Answer("Q3", "C", false, 5));
            answerList.add(new Answer("Q4", "B", false, 2));
            answerList.add(new Answer("Q5", "A", true, 4));
            answerList.add(new Answer("Q6", "A", false, 5));
            Address address1 = new Address("11-111", "Poznan", "Torunska", "11");
            Person person2 = new Person("Robert", "Carlston", 45, address1, answerList, LocalDate.of(1971, Month.JANUARY, 1));
            mongoOperations.insert(person2);
            log.info("(1) inserted v2 = " + person2);

            //findOne
            Query searchPersonQuery = new Query(where("firstname").is("Joe"));
            Person savedPerson = mongoOperations.findOne(searchPersonQuery, Person.class);
            log.info("(2) find after insert = " + savedPerson);
            Person savedPerson2 = mongoOperations.findOne(query(where("firstname").is("Robert")), Person.class);
            log.info("(2b) find after insert = " + savedPerson2);

            //findById
            Person savedPersonV2 = mongoOperations.findById(person.getId(), Person.class);
            log.info("(2B) find after insert = " + savedPersonV2);

            // updateFirst
            searchPersonQuery = new Query(where("firstname").is("Joe"));
            mongoOperations.updateFirst(searchPersonQuery, Update.update("age", "17"),Person.class);

            // find the updated person
            searchPersonQuery = new Query(where("firstname").is("Joe"));
            Person updatedPerson = mongoOperations.findOne(searchPersonQuery, Person.class);
            log.info("(3) updatedPerson = " + updatedPerson);

            //list and order by age
            searchPersonQuery = new Query().with(new Sort(Sort.Direction.DESC, "age"));
            List<Person> personList = mongoOperations.find(searchPersonQuery, Person.class);
            personList.forEach(personEl -> log.info("" + personEl));

            // delete
            searchPersonQuery = new Query(where("firstname").is("Joe"));
            WriteResult remove = mongoOperations.remove(searchPersonQuery, Person.class);
            log.info("deletedResult = " + remove);
            WriteResult removeV2 = mongoOperations.remove(person2);
            log.info("deletedResult v2= " + removeV2);

            // List, it should be empty now.
            personList = mongoOperations.findAll(Person.class);
            log.info("Number of person = " + personList.size());

            //drop collection
            mongoOperations.dropCollection(Person.class);
        }
    }
}
