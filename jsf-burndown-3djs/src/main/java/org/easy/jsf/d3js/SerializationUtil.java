package org.easy.jsf.d3js;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class SerializationUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    static {
        JodaModule jodaModule = new JodaModule();
        MAPPER.configure(com.fasterxml.jackson.databind.SerializationFeature.
            WRITE_DATES_AS_TIMESTAMPS , false);
        MAPPER.registerModule(jodaModule);
    }
    
    public static String toJson(Object o) throws JsonProcessingException {
        return MAPPER.writeValueAsString(o);
    }
}
