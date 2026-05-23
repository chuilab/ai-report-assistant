package com.aireport.ai.prompt;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PromptBuilder {

    public String buildAnalyzePrompt(List<Map<String, Object>> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一名资深数据分析师。请根据以下销售数据，生成一份数据分析报告。\n\n");
        sb.append("数据如下：\n");
        for (Map<String, Object> row : data) {
            sb.append(formatRow(row)).append("\n");
        }
        sb.append("\n");
        sb.append("要求：分析数据趋势和关键指标，给出简要结论。");
        return sb.toString();
    }

    public String buildDailyReportPrompt(List<Map<String, Object>> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是一名资深销售运营分析师。\n");
        sb.append("请根据以下销售数据，生成一份简洁的销售日报。\n\n");
        sb.append("数据如下：\n");
        for (Map<String, Object> row : data) {
            sb.append(formatRow(row)).append("\n");
        }
        sb.append("\n");
        sb.append("要求：\n");
        sb.append("1. 总结销售趋势：分析销售额的整体走势，指出最高点和最低点\n");
        sb.append("2. 分析订单变化：分析订单量的变化趋势，与销售额的关联\n");
        sb.append("3. 给出运营建议：基于数据洞察，给出2-3条可执行的运营建议\n\n");
        sb.append("请用 Markdown 格式输出，结构清晰，使用小标题分隔各部分内容。");
        return sb.toString();
    }

    private String formatRow(Map<String, Object> row) {
        StringBuilder sb = new StringBuilder();
        Object date = row.get("date");
        Object sales = row.get("sales");
        Object orders = row.get("orders");
        Object users = row.get("users");

        if (date != null) sb.append("日期:").append(date);
        if (sales != null) sb.append(" 销售额:").append(sales);
        if (orders != null) sb.append(" 订单量:").append(orders);
        if (users != null) sb.append(" 用户数:").append(users);

        return sb.toString();
    }
}
