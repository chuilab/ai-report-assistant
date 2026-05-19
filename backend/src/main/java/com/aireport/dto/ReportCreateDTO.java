package com.aireport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 报表创建请求DTO
 *
 * @author aireport
 * @since 1.0.0
 */
@Data
@Schema(description = "报表创建请求")
public class ReportCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "报表名称", required = true)
    @NotBlank(message = "报表名称不能为空")
    private String reportName;

    @Schema(description = "报表类型：daily-日报, weekly-周报, monthly-月报", required = true)
    @NotBlank(message = "报表类型不能为空")
    private String reportType;

    @Schema(description = "数据源ID", required = true)
    @NotNull(message = "数据源不能为空")
    private Long dataSourceId;

    @Schema(description = "备注")
    private String remark;

}