package com.himalayas.schoolservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolController {

    @GetMapping("/api/schools")
    public String getSchools() {
        return "List of schools for this tenant";
    }
}
