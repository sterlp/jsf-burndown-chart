package org.easy.jsf.d3js.burndown;

import org.joda.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestIterationBurndown {
    
    @Test(expected = NullPointerException.class)
    public void testDateValidationNull() {
        new IterationBurndown(null, null, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDateValidationEqual() {
        new IterationBurndown(new LocalDate(2000, 1, 1), new LocalDate(2000, 1, 1), 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDateValidationEndSmaller() {
        new IterationBurndown(new LocalDate(2000, 1, 2), new LocalDate(2000, 1, 1), 0);
    }
    
    @Test
    public void testExcludeWeekends() {
        // Saturday to Saturday, 1W + 1 day
        IterationBurndown iteration = new IterationBurndown(new LocalDate(2013, 6, 1), new LocalDate(2013, 6, 8), 200);
        assertEquals(5, iteration.getWorkDays());
        assertEquals(8, iteration.getDaysBetweenStartAndEnd());
    }
    
    @Test
    public void testIncludeWeekends() {
        // Saturday to Saturday, 1W + 1 day
        IterationBurndown iteration = new IterationBurndown(new LocalDate(2013, 6, 1), new LocalDate(2013, 6, 8), 200, true);
        assertEquals(8, iteration.getWorkDays());
        assertEquals(8, iteration.getDaysBetweenStartAndEnd());
    }
    
    @Test
    public void testTwoWeekIteration() {
        // two week from Monday to Friday, including one weekend
        IterationBurndown iteration = new IterationBurndown(new LocalDate(2013, 6, 3), new LocalDate(2013, 6, 14), 200);
        assertEquals(10, iteration.getWorkDays());
        assertEquals(12, iteration.getDaysBetweenStartAndEnd());
    }
    
    @Test
    public void testAddDay() {
        IterationBurndown iteration = new IterationBurndown(new LocalDate(2013, 6, 1), new LocalDate(2013, 6, 8), 200, true);
        iteration.addDay(new LocalDate(2013, 6, 1), 10, null);
        iteration.addDay(new LocalDate(2013, 6, 2), 20, null);
        iteration.addDay(new LocalDate(2013, 6, 2), -10, null);

        assertEquals(10, iteration.getHoursAdded());
        assertEquals(30, iteration.getHoursDone());
        assertEquals(180, iteration.getHoursRemaining());
    }
}