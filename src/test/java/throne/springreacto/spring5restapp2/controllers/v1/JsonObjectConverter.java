package throne.springreacto.spring5restapp2.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import throne.springreacto.spring5restapp2.api.v1.model.CustomerDTO;

public abstract class JsonObjectConverter {

    public static String objectToJsonStirng(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException("Error in parsing object");
        }

    }
}
