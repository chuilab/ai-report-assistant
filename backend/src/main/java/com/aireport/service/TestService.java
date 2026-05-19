package com.aireport.service;

import org.springframework.stereotype.Service;

/**
 * 测试服务
 *
 * @author aireport
 * @since 1.0.0
 */
@Service
public class TestService {

    /**
     * 获取测试消息
     *
     * @return 测试消息
     */
    public String getTestMessage() {
        return "AI Report Assistant Running";
    }

}