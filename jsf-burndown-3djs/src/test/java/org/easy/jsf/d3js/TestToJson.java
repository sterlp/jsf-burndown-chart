package org.easy.jsf.d3js;

import org.junit.Assert;
import org.easy.jsf.d3js.burndown.IterationBurndown;
import org.joda.time.LocalDate;
import org.junit.Test;

public class TestToJson {
    
    @Test
    public void testToJson() throws Exception {
        IterationBurndown burndown = new IterationBurndown(
                new LocalDate(2013, 5, 1), 
                new LocalDate(2013, 5, 31), 250);
        
        burndown.addDay(new LocalDate(2013, 5, 2), 10, null);
        burndown.addDay(new LocalDate(2013, 5, 3), 10, null);
        burndown.addDay(new LocalDate(2013, 5, 3), -10, null);
        
        final String expectedResult =
                "{\"start\":\"2013-05-01\",\"end\":\"2013-05-31\",\"workDays\":23,\"daysBetweenStartAndEnd\":31,\"planedHours\":250,\"hoursRemaining\":240,\"hoursDone\":20,\"hoursAdded\":10,\"burndowns\":[{\"date\":\"2013-05-01\",\"hours\":250,\"comment\":null},{\"date\":\"2013-05-02\",\"hours\":240,\"comment\":null},{\"date\":\"2013-05-03\",\"hours\":230,\"comment\":null},{\"date\":\"2013-05-03\",\"hours\":240,\"comment\":null}],\"timeDomain\":[\"2013-05-01\",\"2013-05-02\",\"2013-05-03\",\"2013-05-06\",\"2013-05-07\",\"2013-05-08\",\"2013-05-09\",\"2013-05-10\",\"2013-05-13\",\"2013-05-14\",\"2013-05-15\",\"2013-05-16\",\"2013-05-17\",\"2013-05-20\",\"2013-05-21\",\"2013-05-22\",\"2013-05-23\",\"2013-05-24\",\"2013-05-27\",\"2013-05-28\",\"2013-05-29\",\"2013-05-30\",\"2013-05-31\"],\"includeWeekEnd\":false}";
        
        System.err.println(SerializationUtil.toJson(burndown));
        Assert.assertEquals(20, burndown.getHoursDone());
        Assert.assertEquals(10, burndown.getHoursAdded());
        Assert.assertEquals(240, burndown.getHoursRemaining());
        Assert.assertEquals(expectedResult, SerializationUtil.toJson(burndown));
    }
}
