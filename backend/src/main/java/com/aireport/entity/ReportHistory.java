package com.aireport.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 报表历史记录实体
 *
 * @author aireport
 * @since 1.0.0
 */
@Data
@TableName("report_history")
public class ReportHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 报表名称
     */
    private String reportName;

    /**
     * 报表类型（日报、周报、月报）
     */
    private String reportType;

    /**
     * 报表内容
     */
    private String content;

    /**
     * 原始数据ID（关联Excel上传记录）
     */
    private Long dataSourceId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除（逻辑删除）
     */
    @TableLogic
    private Integer deleted;

}