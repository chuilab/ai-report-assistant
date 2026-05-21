package com.aireport.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.aireport.common.exception.BusinessException;
import com.aireport.common.result.ResultCode;
import com.aireport.excel.dto.SalesDataDTO;
import com.aireport.excel.listener.SalesDataListener;
import com.aireport.excel.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    @Override
    public Map<String, Object> parseExcel(MultipartFile file) {
        SalesDataListener listener = new SalesDataListener();
        try {
            EasyExcel.read(file.getInputStream(), SalesDataDTO.class, listener)
                .sheet()
                .doRead();
        } catch (IOException e) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR, "Excel 解析失败");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("total", listener.getCount());
        result.put("rows", listener.getRows());
        return result;
    }
}
