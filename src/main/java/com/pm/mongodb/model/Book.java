package com.pm.mongodb.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by pmackiewicz on 2016-02-16.
 */
@Document(collection = "books")
@Data
public class Book {

    @Id
    private String id;

    @NonNull
    private String title;

    @NonNull
    private long pages;


    public Book(String title, long pages) {
        this.title = title;
        this.pages = pages;
    }
}
