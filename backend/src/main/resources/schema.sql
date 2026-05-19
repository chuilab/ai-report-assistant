-- AI Report Assistant 数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS ai_report DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE ai_report;

-- 报表历史表
CREATE TABLE IF NOT EXISTS report_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    report_name VARCHAR(200) NOT NULL COMMENT '报表名称',
    report_type VARCHAR(50) NOT NULL COMMENT '报表类型：daily-日报, weekly-周报, monthly-月报',
    content TEXT COMMENT '报表内容',
    data_source_id BIGINT COMMENT '数据源ID',
    create_by VARCHAR(100) COMMENT '创建人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '是否删除：0-未删除, 1-已删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报表历史记录表';

-- 数据源表（Excel上传记录）
CREATE TABLE IF NOT EXISTS data_source (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
    file_path VARCHAR(500) COMMENT '文件存储路径',
    file_size BIGINT COMMENT '文件大小（字节）',
    file_type VARCHAR(50) COMMENT '文件类型',
    upload_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    create_by VARCHAR(100) COMMENT '上传人',
    deleted INT DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据源表';

-- AI分析记录表
CREATE TABLE IF NOT EXISTS ai_analysis (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    data_source_id BIGINT NOT NULL COMMENT '数据源ID',
    analysis_type VARCHAR(50) COMMENT '分析类型',
    analysis_result TEXT COMMENT '分析结果',
    prompt TEXT COMMENT 'AI提示词',
    ai_model VARCHAR(100) COMMENT '使用的AI模型',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted INT DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI分析记录表';