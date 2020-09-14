package com.se.management.controller;

import com.se.management.model.enums.MessengerType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
@RequestMapping("/messenger")
public class MessengerTypeController {

    @GetMapping()
    public ResponseEntity list() {
        return ResponseEntity.ok(Arrays.asList(MessengerType.values()));
    }
}
