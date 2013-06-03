package org.easy.jsf.d3js.burndown;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * The Iteration Burndown represents the data used
 * to generate the graph of the team effort in the particular iteration.
 */
public class IterationBurndown implements Serializable {
    
    /** Start of the iteration */
    private final LocalDate start;
    /** End of the iteration */
    private final LocalDate end;
    
    /** 
     * The workdays of this iteration/ burndown, does not include the weekend if
     * {@link #includeWeekend} is false
     */
    private final int workDays;
    
    private final boolean includeWeekend;
    private final int daysBetweenStartAndEnd;

    private int planedHours = 0;
    private int hoursRemaining = 0;
    private int hoursDone = 0;
    private int hoursAdded = 0;

    private final List<DailyBurndown> burndowns = new ArrayList<DailyBurndown>();
    private final List<LocalDate> timeDomain = new ArrayList<LocalDate>();
    
    
    public IterationBurndown(LocalDate start, LocalDate end, int planedHours) {
        this(start, end, planedHours, false);
    }

    public IterationBurndown(LocalDate start, LocalDate end, int planedHours, boolean includeWeekend) {
        super();
        this.includeWeekend = includeWeekend;
        this.start = start;
        this.end = end;
        this.planedHours = planedHours;
        this.hoursRemaining = planedHours;
        if (this.start == null) throw new NullPointerException("Start and end date needs to be set. But start is null");
        if (this.end == null) throw new NullPointerException("Start and end date needs to be set. But end is null");
        if (this.end.isBefore(this.start)) throw new IllegalArgumentException("End date must be after the start date");
        if (this.end.isEqual(this.start)) throw new IllegalArgumentException("End date must be after the start date");

        // the initial point something like (01-01-2013 ; 250)
        burndowns.add(new DailyBurndown(start, planedHours));
        // add all days in between start and end date
        daysBetweenStartAndEnd = Days.daysBetween(start, end).getDays() + 1; // we include all days
        workDays = buidTimeDomnain();
    }
    
    /**
     * Builds the time domain and returns also working days in this iteration
     */
    private int buidTimeDomnain() {
        int result = 0;
            
        for (int i = 0; i < daysBetweenStartAndEnd; i++) {
            LocalDate anyDay = start.plusDays(i);
            if (considerDate(anyDay)) {
                timeDomain.add(anyDay);
                ++result;
            }
        }
        return result;
    }
    
    private boolean considerDate(final LocalDate date) {
        return includeWeekend 
                || (date.getDayOfWeek() != DateTimeConstants.SATURDAY
                    && date.getDayOfWeek() != DateTimeConstants.SUNDAY);
    }
    
    public List<LocalDate> getTimeDomain() {
        return timeDomain;
    }
    
    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getPlanedHours() {
        return planedHours;
    }

    public void setPlanedHours(int planedHours) {
        this.planedHours = planedHours;
        this.hoursRemaining = planedHours;
    }

    /** 
     * Getter and setter for serialization, don't use the List directly 
     * @see #addDay(org.joda.time.LocalDate, int, java.lang.String) 
     */
    public List<DailyBurndown> getBurndowns() {
        return burndowns;
    }

    public boolean isIncludeWeekEnd() {
        return includeWeekend;
    }

    /**
     * Adds a new day to the burndown - and does calcualtions. This
     * method should be used to add data to the burn down.
     * 
     * @param day the day to add
     * @param hoursDone the hours done, could also be negative for up-scalling
     * @param comment optional comment
     */
    public void addDay(LocalDate day, int hoursDone, String comment) {
        if (day == null) throw new NullPointerException("Day cannot be null.");
        if (day.isBefore(start)) throw new IllegalArgumentException("Day must be after start of iteration or equal to it.");
        if (day.isAfter(end)) throw new IllegalArgumentException("Day cannot be after the end of the iteration.");

        this.hoursRemaining -= hoursDone;
        if (hoursDone < 0) {
            this.hoursAdded += hoursDone *(-1);
        } else {
            this.hoursDone += hoursDone;
        }
        this.burndowns.add(
                new DailyBurndown(day, 
                hoursRemaining, comment));
    }

    public int getHoursRemaining() {
        return hoursRemaining;
    }

    public int getHoursDone() {
        return hoursDone;
    }

    public int getHoursAdded() {
        return hoursAdded;
    }

    /** 
     * The workdays of this iteration/ burndown, does not include the weekend if
     * {@link #includeWeekend} is false
     */
    public int getWorkDays() {
        return workDays;
    }

    public int getDaysBetweenStartAndEnd() {
        return daysBetweenStartAndEnd;
    }

    @Override
    public String toString() {
        return "IterationBurndown{" + "start=" + start + ", end=" + end + ", workDays=" + workDays + ", daysBetweenStartAndEnd=" + daysBetweenStartAndEnd + ", planedHours=" + planedHours + ", hoursRemaining=" + hoursRemaining + ", hoursDone=" + hoursDone + ", hoursAdded=" + hoursAdded  + ", includeWeekEnd=" + includeWeekend + ", burndowns=" + burndowns +  ", timeDomain=" + timeDomain + '}';
    }
}