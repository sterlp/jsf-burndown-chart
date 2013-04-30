package org.easy.jsf.d3js.burndown;

import java.util.Date;

public class DailyBurndown {
    protected Date date;
    protected int hours;
    protected String comments;

    public DailyBurndown() {
        super();
    }

    public DailyBurndown(Date date, int hours) {
        this();
        this.date = date;
        this.hours = hours;
    }

    public DailyBurndown(Date date, int hours, String comments) {
        this(date, hours);
        this.comments = comments;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
