package org.easy.jsf.d3js.burndown;

import java.io.Serializable;
import org.joda.time.LocalDate;

/**
 * The burn down the team did in one day. A team can have multiple
 * burndowns for one day, e.g.:
 * <li>a up-scalling, tasks added
 * <li>the burn down itself, the tasks done
 */
public class DailyBurndown implements Serializable {
    protected LocalDate date;
    protected int hours;
    protected String comment;

    public DailyBurndown() {
        super();
    }

    public DailyBurndown(LocalDate date, int hours) {
        this();
        this.date = date;
        this.hours = hours;
    }

    public DailyBurndown(LocalDate date, int hours, String comment) {
        this(date, hours);
        this.comment = comment;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
