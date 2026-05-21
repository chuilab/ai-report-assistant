package com.aireport.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalesDataDTO {

    @ExcelProperty("日期")
    @DateTimeFormat("yyyy-MM-dd")
    private LocalDate date;

    @ExcelProperty("销售额")
    private BigDecimal sales;

    @ExcelProperty("订单量")
    private Integer orders;

    @ExcelProperty("用户数")
    private Integer users;
}
