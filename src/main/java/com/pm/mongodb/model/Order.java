package com.pm.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by pmackiewicz on 2016-02-16.
 */
@Document(collection = "orders")
@Data
//@AllArgsConstructor
public class Order {

    @Id
    @Getter
    private String id;

    private String customer_id;

    private Long amount;

    private String status;
}
