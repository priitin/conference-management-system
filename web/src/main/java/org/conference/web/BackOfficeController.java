package org.conference.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackOfficeController {

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }
}
