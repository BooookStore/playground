package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MixinTest {

    @Test
    void mixIn() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String userText = objectMapper.writeValueAsString(new User("foge@huga", "strength-password", "ADMIN"));
        assertEquals("{\"id\":\"foge@huga\",\"password\":\"strength-password\",\"authority\":\"ADMIN\"}", userText);

        objectMapper = new ObjectMapper();
        objectMapper.addMixIn(User.class, UserMixInForIgnoreType.class);
        userText = objectMapper.writeValueAsString(new User("foge@huga", "strength-password", "ADMIN"));
        assertEquals("{\"id\":\"foge@huga\",\"authority\":\"ADMIN\"}", userText);
    }

    public static class User {

        public String id;

        public String password;

        public String authority;

        @JsonCreator
        public User(@JsonProperty("id") String id,
                    @JsonProperty("password") String password,
                    @JsonProperty("authority") String authority) {
            this.id = id;
            this.password = password;
            this.authority = authority;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id) && Objects.equals(password, user.password) && Objects.equals(authority, user.authority);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, password, authority);
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", password='" + password + '\'' +
                    ", authority='" + authority + '\'' +
                    '}';
        }
    }

    public static class UserMixInForIgnoreType {

        @SuppressWarnings("unused")
        @JsonIgnore
        public String password;

    }

}
