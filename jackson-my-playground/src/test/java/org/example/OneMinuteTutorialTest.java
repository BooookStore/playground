package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OneMinuteTutorialTest {

    @Test
    void readValue() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var person = objectMapper.readValue("""
                {
                 "firstName": "book",
                 "lastName": "store"
                }
                """, Person.class);
        assertEquals("firstName: book, lastName: store", person.toString());
    }

    public static class Person {

        public String firstName;

        public String lastName;

        @Override
        public String toString() {
            return String.format("firstName: %s, lastName: %s", firstName, lastName);
        }

    }

}
