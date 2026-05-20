package com.aireport.ai.service;

import com.aireport.ai.client.DeepSeekClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final DeepSeekClient deepSeekClient;

    public String chatWithDeepSeek(String message) {
        log.info("AI对话请求: {}", message);
        String result = deepSeekClient.chat(message);
        log.info("AI对话响应: {}", result);
        return result;
    }
}
