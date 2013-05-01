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


    IterationBurndown burndown;
    
    @PostConstruct
    public void afterLoad() {
        burndown = new IterationBurndown(
                new LocalDate(2013, 5, 1), 
                new LocalDate(2013, 5, 31), 250);
        
        burndown.addDay(new LocalDate(2013, 5, 1), 8, "First Day");
        burndown.addDay(new LocalDate(2013, 5, 2), -16, "Unplanned Bug");
        burndown.addDay(new LocalDate(2013, 5, 2), 40, null);
        burndown.addDay(new LocalDate(2013, 5, 3), 12, null);
        burndown.addDay(new LocalDate(2013, 5, 4), 33, null);
        burndown.addDay(new LocalDate(2013, 5, 5), -44, "Added a new small story");
        burndown.addDay(new LocalDate(2013, 5, 5), 24, null);
    }

    public IterationBurndown getBurndown() {
        return burndown;
    }

    public void setBurndown(IterationBurndown burndown) {
        this.burndown = burndown;
    }
}
