package com.nexlyai.backend.controller;

import com.nexlyai.backend.common.Result;
import com.nexlyai.backend.dto.GenerateCodeRequest;
import com.nexlyai.backend.service.AIService;
import com.nexlyai.backend.vo.GenerateCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/generate")
    public Result<GenerateCodeResponse> generate(@RequestBody GenerateCodeRequest request) {
        String code = aiService.generateCode(request.getPrompt());
        return Result.success(new GenerateCodeResponse(code));
    }
}
