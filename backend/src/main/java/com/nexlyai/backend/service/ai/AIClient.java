package com.nexlyai.backend.service.ai;

/**
 * 统一AI接口（所有模型都要实现它）
 */
public interface AIClient {

    String generate(String prompt);
}
