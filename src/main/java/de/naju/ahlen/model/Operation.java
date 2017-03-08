package de.naju.ahlen.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Steffen on 08.03.2017.
 */
public @Data class Operation {

    private Date date;
    private float duraton;
    private String comment;
}
