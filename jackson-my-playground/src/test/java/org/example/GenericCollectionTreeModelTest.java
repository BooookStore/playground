package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void cantReadObjectToList() {
        assertThrows(MismatchedInputException.class, () -> {
            @SuppressWarnings("unchecked")
            List<String> result = objectMapper.readValue("""
                    {
                     "firstName": "book",
                     "lastName": "store"
                    }
                    """, List.class);
        });
    }

    public record Person(String firstName, String lastName) {

    }

}
