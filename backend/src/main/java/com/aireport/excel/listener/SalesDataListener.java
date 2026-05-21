package com.aireport.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.aireport.excel.dto.SalesDataDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SalesDataListener implements ReadListener<SalesDataDTO> {

    @Getter
    private final List<SalesDataDTO> rows = new ArrayList<>();

    @Override
    public void invoke(SalesDataDTO data, AnalysisContext context) {
        rows.add(data);
        log.info("解析行 {}: {}", rows.size(), data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("解析完成，共 {} 条数据", rows.size());
    }

    public int getCount() {
        return rows.size();
    }
}
