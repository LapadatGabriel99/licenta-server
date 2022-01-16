package com.proiect.licenta.service;

import com.proiect.licenta.repository.ScoredTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoredTestService {

    @Autowired
    private ScoredTestRepository scoredTestRepository;
}
