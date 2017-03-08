package de.naju.ahlen.model;

import java.util.Date;

/**
 * Created by Steffen on 08.03.2017.
 */
public class Operation {

    private Date date;
    private float duraton;
    private String comment;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDuraton() {
        return duraton;
    }

    public void setDuraton(float duraton) {
        this.duraton = duraton;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
