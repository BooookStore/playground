package booookstore.playground.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedResourceController {

    @GetMapping("/resource/{id}")
    public String resource(@PathVariable String id) {
        return "resource " + id;
    }

}
