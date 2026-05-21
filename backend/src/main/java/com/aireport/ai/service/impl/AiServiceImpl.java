package com.aireport.ai.service.impl;

import com.aireport.ai.client.DeepSeekClient;
import com.aireport.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final DeepSeekClient deepSeekClient;

    @Override
    public String chatWithDeepSeek(String message) {
        log.info("AI对话请求: {}", message);
        String result = deepSeekClient.chat(message);
        log.info("AI对话响应: {}", result);
        return result;
    }
}
