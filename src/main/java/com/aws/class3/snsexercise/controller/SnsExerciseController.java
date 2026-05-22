package com.aws.class3.snsexercise.controller;

import com.aws.class3.snsexercise.service.SnsExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sns/geocoding")
public class SnsExerciseController {

    private final SnsExerciseService snsExerciseService;

    public SnsExerciseController(SnsExerciseService snsExerciseService) {
        this.snsExerciseService = snsExerciseService;
    }

    @PostMapping
    public ResponseEntity<String> receive(@RequestBody String message) {
        return ResponseEntity.ok(snsExerciseService.processMessage(message));
    }
}
