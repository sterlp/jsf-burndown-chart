package org.easy.jsf.burndown.sample;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.easy.jsf.d3js.burndown.IterationBurndown;
import org.joda.time.LocalDate;

@ManagedBean
@SessionScoped
public class MyBean implements Serializable {
    private static final long serialVersionUID = 1L;


    private IterationBurndown burndown;
    
    private boolean includeWeekend = false;
    
    @PostConstruct
    public void afterLoad() {
        burndown = new IterationBurndown(
                new LocalDate(2013, 5, 1), 
                new LocalDate(2013, 5, 29), 250, includeWeekend);
        
        burndown.addDay(new LocalDate(2013, 5, 2), 8, "First Day");
        burndown.addDay(new LocalDate(2013, 5, 3), -16, "Unplanned Bug");
        burndown.addDay(new LocalDate(2013, 5, 3), 40, null);
        burndown.addDay(new LocalDate(2013, 5, 4), 12, null);
        burndown.addDay(new LocalDate(2013, 5, 7), 33, null);
        burndown.addDay(new LocalDate(2013, 5, 8), -44, "Added a new small story");
        burndown.addDay(new LocalDate(2013, 5, 8), 24, null);
    }

    public IterationBurndown getBurndown() {
        return burndown;
    }

    public void setBurndown(IterationBurndown burndown) {
        this.burndown = burndown;
    }

    public boolean isIncludeWeekend() {
        return includeWeekend;
    }

    public void setIncludeWeekend(boolean includeWeekend) {
        this.includeWeekend = includeWeekend;
    }
}
