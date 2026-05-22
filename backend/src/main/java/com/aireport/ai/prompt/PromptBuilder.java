package com.aireport.ai.prompt;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PromptBuilder {

    public String buildDailyReportPrompt(List<Map<String, Object>> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一名数据分析师。\n");
        sb.append("请根据以下销售数据，\n");
        sb.append("生成一段日报分析。\n\n");
        sb.append("数据如下：\n");

        for (Map<String, Object> row : data) {
            sb.append(formatRow(row)).append("\n");
        }

        return sb.toString();
    }

    private String formatRow(Map<String, Object> row) {
        StringBuilder sb = new StringBuilder();
        Object date = row.get("date");
        Object sales = row.get("sales");
        Object orders = row.get("orders");

        if (date != null) sb.append(date);
        if (sales != null) sb.append(" 销售额").append(sales);
        if (orders != null) sb.append(" 订单").append(orders);

        return sb.toString();
    }
}
