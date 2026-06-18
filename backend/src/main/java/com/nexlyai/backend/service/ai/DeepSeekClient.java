package com.nexlyai.backend.service.ai;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeepSeekClient implements AIClient{

    private static final String API_URL = "https://api.deepseek.com/chat/completions";

    private static final String API_KEY = "sk-9b814cee9a304afc95909ba4c0b22863";

    private final RestTemplate restTemplate =  new RestTemplate();

    @Override
    public String generate(String prompt) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-v4-flash");

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        String fullPrompt =
                "你是一个专业前端工程师，请用以下格式回答：\n" +
                        "1. 先简短解释\n" +
                        "2. 再给代码（必须用```包裹）\n" +
                        "3. 最后给注意事项\n\n" +
                        "用户问题：" + prompt;
        userMsg.put("content", fullPrompt);

        messages.add(userMsg);

        requestBody.put("messages", messages);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        // 告诉别人发的是json格式数据
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 把密钥放请求头
        headers.setBearerAuth(API_KEY);

        // 把请求头和请求体打包起来
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 接收返回的完整响应 存到response里  <String>：代表响应里的正文我们要存成字符串类型
            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // 取出返回的完整 JSON 字符串
            String body = response.getBody();

            // SON 解析器，把文本字符串转成程序能读取的树形 JSON 结构
            ObjectMapper mapper = new ObjectMapper();
            // 把返回的 JSON 字符串 body 加载成一棵 JSON 树 root
            JsonNode root = mapper.readTree(body);

            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "AI调用失败";
        }
    }
}
