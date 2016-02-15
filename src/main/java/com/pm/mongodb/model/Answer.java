package com.pm.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by pmackiewicz on 2016-02-11.
 */
@Data
@AllArgsConstructor
public class Answer {

    private String questionNumber;

    private String answerNumber;

    private boolean correct;

    private int pointsNumber;
}
