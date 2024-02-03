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

    @Test
    void writeValue() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        var output = objectMapper.writeValueAsString(new Person("book", "store"));
        assertEquals("{\"firstName\":\"book\",\"lastName\":\"store\"}", output);
    }

    public static class Person {

        @SuppressWarnings("unused") // jackson use
        public Person() {

        }

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String firstName;

        public String lastName;

        @Override
        public String toString() {
            return String.format("firstName: %s, lastName: %s", firstName, lastName);
        }

    }

}
