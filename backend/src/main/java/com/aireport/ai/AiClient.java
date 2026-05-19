package com.aireport.ai;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.aireport.common.exception.BusinessException;
import com.aireport.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * AI客户端 - 预留接口
 * 用于调用OpenAI/DeepSeek等AI服务
 *
 * @author aireport
 * @since 1.0.0
 */
@Slf4j
@Component
public class AiClient {

    @Value("${ai.deepseek.api-key:}")
    private String deepseekApiKey;

    @Value("${ai.deepseek.base-url:https://api.deepseek.com/v1}")
    private String deepseekBaseUrl;

    @Value("${ai.deepseek.model:deepseek-chat}")
    private String deepseekModel;

    /**
     * 调用OpenAI接口（预留）
     *
     * @param prompt 提示词
     * @return AI返回结果
     */
    public String callOpenAI(String prompt) {
        if (deepseekApiKey == null || deepseekApiKey.isEmpty()) {
            throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "deepseek API Key未配置");
        }

        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", deepseekModel);
            requestBody.put("messages", new Map[]{
                    Map.of("role", "user", "content", prompt)
            });

            HttpResponse response = HttpRequest.post(deepseekBaseUrl + "/chat/completions")
                    .header("Authorization", "Bearer " + deepseekBaseUrl)
                    .header("Content-Type", "application/json")
                    .body(cn.hutool.json.JSONUtil.toJsonStr(requestBody))
                    .timeout(30000)
                    .execute();

            if (!response.isOk()) {
                log.error("deepseekAI调用失败: {}", response.body());
                throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "AI服务调用失败");
            }

            // 解析返回结果（简化版，后续可优化）
            return response.body();

        } catch (Exception e) {
            log.error("deepseekAI调用异常: {}", e.getMessage(), e);
            throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "AI服务异常");
        }
    }

    /**
     * 调用DeepSeek接口（预留）
     *
     * @param prompt 提示词
     * @return AI返回结果
     */
    public String callDeepSeek(String prompt) {
        if (deepseekApiKey == null || deepseekApiKey.isEmpty()) {
            throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "DeepSeek API Key未配置");
        }

        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", deepseekModel);
            requestBody.put("messages", new Map[]{
                    Map.of("role", "user", "content", prompt)
            });

            HttpResponse response = HttpRequest.post(deepseekBaseUrl + "/chat/completions")
                    .header("Authorization", "Bearer " + deepseekApiKey)
                    .header("Content-Type", "application/json")
                    .body(cn.hutool.json.JSONUtil.toJsonStr(requestBody))
                    .timeout(30000)
                    .execute();

            if (!response.isOk()) {
                log.error("DeepSeek调用失败: {}", response.body());
                throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "AI服务调用失败");
            }

            return response.body();

        } catch (Exception e) {
            log.error("DeepSeek调用异常: {}", e.getMessage(), e);
            throw new BusinessException(ResultCode.AI_SERVICE_ERROR, "AI服务异常");
        }
    }

}