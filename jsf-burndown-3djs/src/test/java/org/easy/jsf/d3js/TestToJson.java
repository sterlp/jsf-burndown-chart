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
        
        final String expectedResult =
                "{\"start\":\"2013-05-01\",\"end\":\"2013-05-31\",\"planedHours\":250,\"burndowns\":[{\"date\":\"2013-05-01\",\"hours\":250,\"comment\":null}],\"includeWeekEnd\":false,\"timeDomain\":[\"2013-05-01\",\"2013-05-02\",\"2013-05-03\",\"2013-05-06\",\"2013-05-07\",\"2013-05-08\",\"2013-05-09\",\"2013-05-10\",\"2013-05-13\",\"2013-05-14\",\"2013-05-15\",\"2013-05-16\",\"2013-05-17\",\"2013-05-20\",\"2013-05-21\",\"2013-05-22\",\"2013-05-23\",\"2013-05-24\",\"2013-05-27\",\"2013-05-28\",\"2013-05-29\",\"2013-05-30\",\"2013-05-31\"]}";
        
        System.err.println(SerializationUtil.toJson(burndown));
        Assert.assertEquals(expectedResult, SerializationUtil.toJson(burndown));
    }
}
