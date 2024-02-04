package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TreeModelTest {

    ObjectMapper objectMapper = new ObjectMapper();

    JsonNode root;

    @BeforeEach
    void initRoot() throws JsonProcessingException {
        root = objectMapper.readTree("""
                {
                    "NB001": {
                        "profile": {
                            "firstName": "book",
                            "lastName": "store",
                            "age": 25
                        },
                        "type": [
                            "software engineer"
                        ]
                    }
                }
                """);
    }

    @Test
    void readTree() {
        String firstName = root.get("NB001").get("profile").get("firstName").asText();
        assertEquals("book", firstName);

        int age = root.get("NB001").get("profile").get("age").asInt();
        assertEquals(25, age);
    }

    @Test
    void put() {
        root.withObject("NB001").put("address", "123456");
        String address = root.get("NB001").get("address").asText();
        assertEquals("123456", address);
    }

    @Test
    void readTreeNotExist() {
        JsonNode notExist = root.get("notExist");
        assertNull(notExist);
    }

    @Test
    void treeToValue() throws JsonProcessingException {
        Profile profile = objectMapper.treeToValue(root.get("NB001").get("profile"), Profile.class);
        assertEquals(new Profile("book", "store", 25), profile);
    }

    public record Profile(String firstName, String lastName, int age) {
    }

}
