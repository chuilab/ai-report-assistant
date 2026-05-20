package com.aireport.controller;

import com.aireport.ai.service.AiService;
import com.aireport.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI接口", description = "AI对话测试接口")
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @Operation(summary = "AI测试接口", description = "发送消息给DeepSeek AI并返回回复")
    @GetMapping("/test")
    public Result<String> test(
            @Parameter(description = "发送给AI的消息") @RequestParam(defaultValue = "你好") String msg) {
        String reply = aiService.chatWithDeepSeek(msg);
        return Result.success(reply);
    }
}
