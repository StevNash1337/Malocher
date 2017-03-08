package de.naju.ahlen.model;

import java.util.Date;

/**
 * Created by Steffen on 08.03.2017.
 */
public class Operation {

    private Date date;
    private float duration;
    private String comment;

    public Operation() {
        this.date = new Date();
        this.duration = 0;
        this.comment = "";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
