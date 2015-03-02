package org.easy.jsf.d3js;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;

@FacesComponent("org.easy.jsf.d3js.GraphContainer")
public class GraphContainer extends UINamingContainer {
    
    public String toJson(Object o) throws JsonProcessingException {
        return SerializationUtil.toJson(o);
    }
}
