package com.aireport.excel.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ExcelService {

    Map<String, Object> parseExcel(MultipartFile file);
}
