package com.aireport.controller;

import com.aireport.ai.prompt.PromptBuilder;
import com.aireport.ai.service.AiService;
import com.aireport.common.result.Result;
import com.aireport.dto.AnalyzeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "报表分析", description = "AI报表分析接口")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final AiService aiService;
    private final PromptBuilder promptBuilder;

    @Operation(summary = "AI分析报表", description = "上传销售数据，生成AI数据分析")
    @PostMapping("/analyze")
    public Result<Map<String, String>> analyze(@Valid @RequestBody AnalyzeRequest request) {
        String prompt = promptBuilder.buildAnalyzePrompt(request.getData());
        String aiResult = aiService.chatWithDeepSeek(prompt);
        return Result.success(Map.of("summary", aiResult));
    }

    @Operation(summary = "AI销售日报", description = "上传销售数据，生成AI销售日报")
    @PostMapping("/daily")
    public Result<Map<String, String>> daily(@Valid @RequestBody AnalyzeRequest request) {
        String prompt = promptBuilder.buildDailyReportPrompt(request.getData());
        String aiResult = aiService.chatWithDeepSeek(prompt);
        return Result.success(Map.of("summary", aiResult));
    }
}
