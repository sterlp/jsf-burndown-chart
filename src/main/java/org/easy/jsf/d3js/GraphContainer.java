package org.easy.jsf.d3js;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent("org.easy.jsf.d3js.GraphContainer")
public class GraphContainer extends UINamingContainer {

    private static ObjectMapper MAPPER = new ObjectMapper();
    
    public String toJson(Object o) throws JsonProcessingException {
        System.out.println("toJson: " + o);
        return MAPPER.writeValueAsString(o);
    }
}
