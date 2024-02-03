package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
    void readAsList() throws JsonProcessingException {
        @SuppressWarnings("unchecked")
        List<Integer> result = objectMapper.readValue("""
                [1, 2, 3]
                """, List.class);
        assertEquals(List.of(1, 2, 3), result);
    }

    @Test
    void cantReadObjectToList() {
        assertThrows(MismatchedInputException.class, () -> objectMapper.readValue("""
                {
                 "firstName": "book",
                 "lastName": "store"
                }
                """, List.class));
    }

    @Test
    void readAsMapPersonRecord() throws JsonProcessingException {
        var people = objectMapper.readValue("""
                {
                 "NB001": {
                  "firstName": "book",
                  "lastName": "store"
                 }
                }
                """, new TypeReference<Map<Id, Person>>() {
        });
        assertEquals(Map.of(new Id("NB001"), new Person("book", "store")), people);
    }

    public record Id(String value) {
    }

    public record Person(String firstName, String lastName) {
    }

}
