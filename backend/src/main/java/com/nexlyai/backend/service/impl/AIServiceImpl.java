package com.nexlyai.backend.service.impl;

import com.nexlyai.backend.service.AIService;
import org.springframework.stereotype.Service;

@Service
public class AIServiceImpl implements AIService {

    @Override
    public String generateCode(String prompt) {
        return """
                // AI生成代码
                
                // 用户需求：
                """ + prompt;
    }
}
