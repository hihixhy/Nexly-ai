package com.nexlyai.backend.service.impl;

import com.nexlyai.backend.service.AIService;
import com.nexlyai.backend.service.ai.AIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    @Autowired
    private AIClient aiClient;

    @Override
    public String generateCode(String prompt) {
        return aiClient.generate(prompt);
    }
}
