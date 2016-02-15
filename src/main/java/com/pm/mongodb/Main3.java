package com.pm.mongodb;

import com.pm.mongodb.model.Address;
import com.pm.mongodb.model.Answer;
import com.pm.mongodb.model.Person;
import com.pm.mongodb.repository.PersonRepository;
import configuration.SpringMongoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Main3 {

    public static final Logger log = LoggerFactory.getLogger(Main3.class);

    public static void main(String[] args) throws Exception {
        new Main3().run();
    }

    public Main3() {
    }

    private void run() throws Exception {

        try (ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class)) {
            MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

            PersonRepository personRepository = ctx.getBean(PersonRepository.class);

            personRepository.deleteAll();

            List<Answer> answerList = new ArrayList<>();
            answerList.add(new Answer("Q1", "A", true, 10));
            answerList.add(new Answer("Q2", "B", true, 4));
            answerList.add(new Answer("Q3", "C", false, 5));
            answerList.add(new Answer("Q4", "C", true, 2));
            answerList.add(new Answer("Q5", "B", false, 4));
            answerList.add(new Answer("Q6", "A", false, 5));
            Address address = new Address("88-888", "Gdansk", "Piastowska", "21");
            Person p = new Person("Andzelika", "Kerber", 27, address, answerList, LocalDate.of(1987, Month.JANUARY, 1));
            personRepository.save(p);
            Address address1 = new Address("88-888", "Gdansk", "Piastowska", "21");
            p = new Person("Pawel", "Kukiz", 38, address1, answerList, LocalDate.of(1978, Month.JANUARY, 1));
            personRepository.save(p);

            answerList = new ArrayList<>();
            answerList.add(new Answer("Q1", "B", false, 10));
            answerList.add(new Answer("Q2", "B", true, 4));
            answerList.add(new Answer("Q3", "C", false, 5));
            Address address2 = new Address("88-888", "Gdansk", "Piastowska", "21");
            p = new Person("Pawel", "Duda", 17, address2, answerList, LocalDate.of(1987, Month.JANUARY, 1));
            personRepository.save(p);
            final Address address3 = new Address("88-888", "Gdansk", "Piastowska", "21");
            p = new Person("Krzysztof", "Nowakowski", 15, address3, answerList, LocalDate.of(2001, Month.JANUARY, 1));
            personRepository.save(p);

            List<Person> allPersons = personRepository.findAll();
            allPersons.forEach(person -> log.info("" + person));

            List<Person> personList = personRepository.searchByFirstName("Pawel");
            personList.forEach(person -> log.info("" + person));

            personList = personRepository.searchByFirstNameAndReturnName("Pawel");
            personList.forEach(person -> log.info("" + person));

            personList = personRepository.findByAgeGreaterThan(20);
            personList.forEach(person -> log.info("" + person));

            personList = personRepository.findByAgeBetween(20, 30);
            personList.forEach(person -> log.info("" + person));

            personList = personRepository.searchByFirstnameAndLastname("Pawel", "Duda");
            personList.forEach(person -> log.info("" + person));

            personRepository.deleteAll();
        }
    }
}
