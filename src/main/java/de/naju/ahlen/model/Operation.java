package de.naju.ahlen.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Steffen on 08.03.2017.
 */
public class Operation {

    // TODO: Ich glaube Date läuft bald aus. Calender ist glaube ich der neue heiße Scheiß!
    private Date date;
    private float duration;
    private String comment;
    private SimpleDateFormat sdf;

    public Operation() {
        this.date = new Date();
        this.duration = 0;
        this.comment = "";
        sdf = new SimpleDateFormat("dd.MM.yy");
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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append("Datum: ")
                .append(sdf.format(date))
                .append("; Dauer: ")
                .append(duration)
                .append("; Kommentar: ")
                .append(comment)
                .toString();
    }
}
