package com.aireport.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AnalyzeRequest {

    @NotEmpty(message = "数据不能为空")
    private List<Map<String, Object>> data;
}
