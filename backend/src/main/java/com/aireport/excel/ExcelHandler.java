package com.aireport.excel;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.aireport.common.exception.BusinessException;
import com.aireport.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel处理器 - 预留接口
 * 用于Excel文件的读取和生成
 *
 * @author aireport
 * @since 1.0.0
 */
@Slf4j
@Component
public class ExcelHandler {

    /**
     * 读取Excel文件（支持xls和xlsx）
     *
     * @param file 上传的Excel文件
     * @return Excel数据列表
     */
    public List<Map<String, Object>> readExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.FILE_FORMAT_ERROR, "文件不能为空");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.endsWith(".xls") && !filename.endsWith(".xlsx"))) {
            throw new BusinessException(ResultCode.FILE_FORMAT_ERROR, "仅支持Excel格式文件");
        }

        try {
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            List<Map<String, Object>> rows = reader.readAll();
            log.info("成功读取Excel文件，共{}行数据", rows.size());
            return rows;
        } catch (IOException e) {
            log.error("读取Excel文件失败: {}", e.getMessage(), e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR, "读取Excel文件失败");
        }
    }

    /**
     * 读取Excel文件并转为指定对象列表
     *
     * @param file  上传的Excel文件
     * @param clazz 目标对象类型
     * @return 对象列表
     */
    public <T> List<T> readExcelToObject(MultipartFile file, Class<T> clazz) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.FILE_FORMAT_ERROR, "文件不能为空");
        }

        try {
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            List<T> list = reader.readAll(clazz);
            log.info("成功读取Excel文件，共{}条数据", list.size());
            return list;
        } catch (IOException e) {
            log.error("读取Excel文件失败: {}", e.getMessage(), e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR, "读取Excel文件失败");
        }
    }

    /**
     * 生成Excel文件（输出到流）
     *
     * @param data    数据列表
     * @param headers 表头（可选）
     * @param out     输出流
     */
    public void writeExcel(List<Map<String, Object>> data, Map<String, String> headers, OutputStream out) {
        try {
            ExcelWriter writer = ExcelUtil.getWriter(true);

            // 设置表头
            if (headers != null && !headers.isEmpty()) {
//                writer.setHeader(headers);
            }

            // 写入数据
            writer.write(data, true);
            writer.flush(out, true);
            writer.close();

            log.info("成功生成Excel文件，共{}行数据", data.size());
        } catch (Exception e) {
            log.error("生成Excel文件失败: {}", e.getMessage(), e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR, "生成Excel文件失败");
        }
    }

    /**
     * 生成Excel文件（从对象列表）
     *
     * @param list  对象列表
     * @param out   输出流
     * @param clazz 对象类型
     */
    public <T> void writeExcelFromObject(List<T> list, OutputStream out, Class<T> clazz) {
        try {
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.write(list, true);
            writer.flush(out, true);
            writer.close();

            log.info("成功生成Excel文件，共{}行数据", list.size());
        } catch (Exception e) {
            log.error("生成Excel文件失败: {}", e.getMessage(), e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR, "生成Excel文件失败");
        }
    }

}