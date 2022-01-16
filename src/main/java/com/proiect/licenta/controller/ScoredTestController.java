package com.proiect.licenta.controller;

import com.proiect.licenta.converter.ScoredTestConverter;
import com.proiect.licenta.service.ScoredTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scored-test")
public class ScoredTestController {

    @Autowired
    private ScoredTestService scoredTestService;

    @Autowired
    private ScoredTestConverter scoredTestConverter;
}
