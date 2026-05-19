package com.aireport.report;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 报表生成器 - 预留接口
 * 用于自动生成日报、周报等报表
 *
 * @author aireport
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReportGenerator {

    /**
     * 生成日报模板
     *
     * @param data 数据内容
     * @return 日报文本
     */
    public String generateDailyReport(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();

        sb.append("【日报】").append(DateUtil.today()).append("\n\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━\n\n");

        // 今日工作内容
        sb.append("一、今日工作内容\n");
        if (data.containsKey("tasks")) {
            List<String> tasks = (List<String>) data.get("tasks");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
        }
        sb.append("\n");

        // 今日完成情况
        sb.append("二、今日完成情况\n");
        if (data.containsKey("completed")) {
            sb.append(data.get("completed").toString()).append("\n");
        }
        sb.append("\n");

        // 明日计划
        sb.append("三、明日计划\n");
        if (data.containsKey("tomorrow")) {
            List<String> tomorrow = (List<String>) data.get("tomorrow");
            for (int i = 0; i < tomorrow.size(); i++) {
                sb.append(i + 1).append(". ").append(tomorrow.get(i)).append("\n");
            }
        }
        sb.append("\n");

        // 问题与建议
        sb.append("四、问题与建议\n");
        if (data.containsKey("issues")) {
            sb.append(data.get("issues").toString()).append("\n");
        }

        sb.append("\n━━━━━━━━━━━━━━━━━━━━\n");

        return sb.toString();
    }

    /**
     * 生成周报模板
     *
     * @param data 数据内容
     * @return 周报文本
     */
    public String generateWeeklyReport(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();

        Date now = new Date();
        Date weekStart = DateUtil.offsetDay(now, -7);

        sb.append("【周报】").append(DateUtil.format(weekStart, "yyyy-MM-dd"))
                .append(" 至 ").append(DateUtil.today()).append("\n\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━\n\n");

        // 本周工作总结
        sb.append("一、本周工作总结\n");
        if (data.containsKey("summary")) {
            sb.append(data.get("summary").toString()).append("\n");
        }
        sb.append("\n");

        // 本周重点项目
        sb.append("二、本周重点项目\n");
        if (data.containsKey("projects")) {
            List<Map<String, String>> projects = (List<Map<String, String>>) data.get("projects");
            for (Map<String, String> project : projects) {
                sb.append("项目：").append(project.getOrDefault("name", ""))
                        .append(" - ").append(project.getOrDefault("status", ""))
                        .append("\n");
            }
        }
        sb.append("\n");

        // 本周数据分析
        sb.append("三、本周数据分析\n");
        if (data.containsKey("analysis")) {
            sb.append(data.get("analysis").toString()).append("\n");
        }
        sb.append("\n");

        // 下周计划
        sb.append("四、下周计划\n");
        if (data.containsKey("nextWeek")) {
            List<String> nextWeek = (List<String>) data.get("nextWeek");
            for (int i = 0; i < nextWeek.size(); i++) {
                sb.append(i + 1).append(". ").append(nextWeek.get(i)).append("\n");
            }
        }
        sb.append("\n");

        // 经验与反思
        sb.append("五、经验与反思\n");
        if (data.containsKey("reflection")) {
            sb.append(data.get("reflection").toString()).append("\n");
        }

        sb.append("\n━━━━━━━━━━━━━━━━━━━━\n");

        return sb.toString();
    }

    /**
     * 生成数据分析报告
     *
     * @param dataList 数据列表
     * @return 分析报告
     */
    public String generateDataAnalysis(List<Map<String, Object>> dataList) {
        StringBuilder sb = new StringBuilder();

        sb.append("【数据分析报告】").append(DateUtil.today()).append("\n\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━\n\n");

        // 数据概览
        sb.append("一、数据概览\n");
        sb.append("总数据量：").append(dataList.size()).append("条\n\n");

        // 字段分析
        if (!dataList.isEmpty()) {
            Map<String, Object> firstRow = dataList.get(0);
            sb.append("二、字段列表\n");
            sb.append(firstRow.keySet().stream()
                    .collect(Collectors.joining(", ", "字段：", "\n")));
            sb.append("\n");

            // 统计信息（简化版）
            sb.append("三、统计信息\n");
            sb.append("数据已导入系统，可进行进一步分析。\n\n");
        }

        sb.append("━━━━━━━━━━━━━━━━━━━━\n");

        return sb.toString();
    }

}