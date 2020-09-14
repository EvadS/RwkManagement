package com.se.management.controller;


import com.se.management.model.enums.SkillName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/skill")
public class SkillsController {

    @GetMapping()
    public ResponseEntity list() {
        return ResponseEntity.ok(Arrays.asList(SkillName.values()));
    }
}
