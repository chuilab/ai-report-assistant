package com.aireport.ai.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aireport.ai.dto.DeepSeekRequest;
import com.aireport.ai.dto.DeepSeekResponse;
import com.aireport.common.exception.BusinessException;
import com.aireport.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DeepSeekClient {

    @Value("${ai.deepseek.api-key:}")
    private String apiKey;

    @Value("${ai.deepseek.base-url:https://api.deepseek.com/v1}")
    private String baseUrl;

    @Value("${ai.deepseek.model:deepseek-chat}")
    private String model;

    public String chat(String userMessage) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "DeepSeek API Key未配置");
        }

        DeepSeekRequest request = DeepSeekRequest.builder()
                .model(model)
                .messages(List.of(
                        DeepSeekRequest.Message.builder()
                                .role("user")
                                .content(userMessage)
                                .build()
                ))
                .build();

        try {
            HttpResponse response = HttpRequest.post(baseUrl + "/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(request))
                    .timeout(60000)
                    .execute();

            if (!response.isOk()) {
                log.error("DeepSeek调用失败, status={}, body={}", response.getStatus(), response.body());
                String errorMsg = extractErrorMessage(response.body());
                throw new BusinessException(ResultCode.AI_SERVICE_ERROR, errorMsg);
            }

            DeepSeekResponse deepSeekResponse = JSONUtil.toBean(response.body(), DeepSeekResponse.class);

            if (deepSeekResponse.getChoices() == null || deepSeekResponse.getChoices().isEmpty()) {
                throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "AI未返回有效内容");
            }

            return deepSeekResponse.getChoices().get(0).getMessage().getContent();

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("DeepSeek调用异常: {}", e.getMessage(), e);
            throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "AI服务异常");
        }
    }

    private String extractErrorMessage(String responseBody) {
        try {
            JSONObject json = JSONUtil.parseObj(responseBody);
            JSONObject error = json.getJSONObject("error");
            if (error != null && error.containsKey("message")) {
                return error.getStr("message");
            }
        } catch (Exception ignored) {
        }
        return "AI服务调用失败";
    }
}
