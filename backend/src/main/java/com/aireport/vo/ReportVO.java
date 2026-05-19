package com.aireport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 报表信息VO
 *
 * @author aireport
 * @since 1.0.0
 */
@Data
@Schema(description = "报表信息")
public class ReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "报表ID")
    private Long id;

    @Schema(description = "报表名称")
    private String reportName;

    @Schema(description = "报表类型")
    private String reportType;

    @Schema(description = "报表内容")
    private String content;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}