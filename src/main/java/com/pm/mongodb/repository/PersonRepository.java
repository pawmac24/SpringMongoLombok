package com.pm.mongodb.repository;

import com.pm.mongodb.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pmackiewicz on 2016-02-10.
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

    @Query("{'firstname' : ?0}")
    public List<Person> searchByFirstName(String firstname);

    @Query(value="{ 'firstname' : ?0 }", fields="{ 'firstname' : 1, 'lastname' : 1}")
    public List<Person> searchByFirstNameAndReturnName(String firstname);

    public List<Person>findByAgeGreaterThan(int age);

    public List<Person> findByAgeBetween(int from, int to);

    @Query("{'firstname' : ?0, 'lastname' : ?1}")
    public List<Person> searchByFirstnameAndLastname(String firstname, String lastname);
}
