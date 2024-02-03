package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericCollectionTreeModelTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void readAsMap() throws JsonProcessingException {
        @SuppressWarnings("unchecked")
        Map<String, String> result = objectMapper.readValue("""
                {
                 "firstName": "book",
                 "lastName": "store"
                }
                """, Map.class);
        assertEquals(Map.of("firstName", "book", "lastName", "store"), result);
    }

    public record Person(String firstName, String lastName) {

    }

}
