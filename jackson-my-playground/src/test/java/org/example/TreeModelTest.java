package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void readTreeIllegalType() {
        int firstName = root.get("NB001").get("profile").get("firstName").asInt();
        assertEquals(0, firstName);

        String age = root.get("NB001").get("profile").get("age").asText();
        assertEquals("25", age);
    }

    @Test
    void checkType() {
        JsonNode firstNameNode = root.get("NB001").get("profile").get("firstName");
        assertTrue(firstNameNode.isTextual());
        assertFalse(firstNameNode.isInt());

        JsonNode ageNode = root.get("NB001").get("profile").get("age");
        assertTrue(ageNode.isInt());
        assertFalse(ageNode.isTextual());
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
    void find() {
        var firstName = root.findValue("firstName").asText();
        assertEquals("book", firstName);
    }

    @Test
    void createObjectModel() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.putPOJO("NB002", new Profile("book", "store2", 26));
        assertEquals("{\"NB002\":{\"firstName\":\"book\",\"lastName\":\"store2\",\"age\":26}}", objectMapper.writeValueAsString(objectNode));
    }

    public record Profile(String firstName, String lastName, int age) {
    }

    @Nested
    class TreeToValueTest {

        @Test
        void treeToValue() throws JsonProcessingException {
            Profile profile = objectMapper.treeToValue(root.get("NB001").get("profile"), Profile.class);
            assertEquals(new Profile("book", "store", 25), profile);

            List<String> type = objectMapper.treeToValue(root.at("/NB001/type"), new TypeReference<>() {
            });
            assertEquals(List.of("software engineer"), type);
        }

        @Test
        void treeToValueNestedJson() throws JsonProcessingException {
            JsonNode root = objectMapper.readTree("""
                    {
                        "NB001": {
                            "profile": {
                                "firstName": "book",
                                "lastName": "store",
                                "age": 25,
                                "address": {
                                    "country": "Japan",
                                    "countryCode": "JPN"
                                }
                            },
                            "type": [
                                "software engineer"
                            ]
                        }
                    }
                    """);
            var profileNestedJson = objectMapper.treeToValue(root.get("NB001").get("profile"), ProfileNestedJson.class);

            assertEquals(new ProfileNestedJson("book", "store", 25, new ProfileNestedJsonAddress("Japan", "JPN")), profileNestedJson);
        }

        record ProfileNestedJson(String firstName, String lastName, int age, ProfileNestedJsonAddress address) {
        }

        record ProfileNestedJsonAddress(String country, String countryCode) {
        }
    }
}
