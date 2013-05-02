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
    private LocalDate start;
    /** End of the iteration */
    private LocalDate end;
    private int planedHours;
    private int hoursRemaining;
    private List<DailyBurndown> burndowns = new ArrayList<DailyBurndown>();
    
    private boolean includeWeekEnd = false;
    
    public IterationBurndown() {
        super();
    }

    public IterationBurndown(LocalDate start, LocalDate end, int planedHours) {
        this();
        this.start = start;
        this.end = end;
        this.planedHours = planedHours;
        this.hoursRemaining = planedHours;
    }

    public IterationBurndown(LocalDate start, LocalDate end, int planedHours, boolean includeWeekEnd) {
        this(start, end, planedHours);
        this.includeWeekEnd = includeWeekEnd;
    }
    
    public List<LocalDate> getTimeDomain() {
        List<LocalDate> result = new ArrayList<LocalDate>();
        
        if (start != null && end != null) {
            result.add(start);
            // add all days in between start and end date
            int totalDay = Days.daysBetween(start, end).getDays();
            for (int i = 1; i < totalDay; i++) {
                LocalDate anyDay = start.plusDays(i);
                if (includeWeekEnd 
                    || (anyDay.getDayOfWeek() != DateTimeConstants.SATURDAY
                        && anyDay.getDayOfWeek() != DateTimeConstants.SUNDAY)) {
                    result.add(start.plusDays(i));
                }
            }
            result.add(end);
        } else {
            throw new IllegalStateException("Start and end date needs to be set. But one is null");
        }
        return result;
    }
    
    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public int getPlanedHours() {
        return planedHours;
    }

    public void setPlanedHours(int planedHours) {
        this.planedHours = planedHours;
        this.hoursRemaining = planedHours;
    }

    public List<DailyBurndown> getBurndowns() {
        return burndowns;
    }

    public void setBurndowns(List<DailyBurndown> burndowns) {
        this.burndowns = burndowns;
    }

    public boolean isIncludeWeekEnd() {
        return includeWeekEnd;
    }

    public void setIncludeWeekEnd(boolean includeWeekEnd) {
        this.includeWeekEnd = includeWeekEnd;
    }

    /**
     * Adds a new day to the burndown.
     * @param day the day to add
     * @param hoursDone the hours done, could also be negative for up-scalling
     * @param comment optional comment
     */
    public void addDay(LocalDate day, int hoursDone, String comment) {
        this.hoursRemaining -= hoursDone;
        this.burndowns.add(
                new DailyBurndown(day, 
                hoursRemaining, comment));
    }
}
