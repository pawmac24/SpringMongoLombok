package com.pm.mongodb.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by pmackiewicz on 2016-02-10.
 */
@Document(collection = "persons")
@ToString
public class Person {

    @Id
    @Getter
    private String id;

    @Getter @Setter
    private String firstname;

    @Getter @Setter
    private String lastname;

    @Getter @Setter
    private Integer age;

    @Getter @Setter
    private Address address;

    @Getter @Setter
    private List<Answer> answer;

    @Getter @Setter
    private LocalDate birthDate;

    @Version
    Long version;

    public Person(String firstname, String lastname, Integer age, Address address, List<Answer> answer, LocalDate birthDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.address = address;
        this.answer = answer;
        this.birthDate = birthDate;
    }
}