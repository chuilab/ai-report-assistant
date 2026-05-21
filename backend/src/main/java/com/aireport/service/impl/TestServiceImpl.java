package com.aireport.service.impl;

import com.aireport.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String getTestMessage() {
        return "AI Report Assistant Running";
    }
}
