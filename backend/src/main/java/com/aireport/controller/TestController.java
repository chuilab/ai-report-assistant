package com.aireport.controller;

import com.aireport.common.result.Result;
import com.aireport.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author aireport
 * @since 1.0.0
 */
@Tag(name = "测试接口", description = "用于测试系统是否正常运行")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    /**
     * 测试接口
     */
    @Operation(summary = "测试接口", description = "返回系统运行状态")
    @GetMapping
    public Result<String> test() {
        String message = testService.getTestMessage();
        return Result.success(message);
    }

}