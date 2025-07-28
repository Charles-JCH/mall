package org.example.objectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcess {

    public static void main(String[] args) {
        String json = "{\"user\": \"tom\", \"age\": \"20\", \"detail\": {\"address\": \"辽宁\", \"email\": \"123@163.com\"}}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(json);
            String sex = jsonNode.path("user").path("sex").asText();
            System.out.println(sex);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
