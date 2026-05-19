package com.aireport.common.result;

import lombok.Getter;

/**
 * 返回状态码枚举
 *
 * @author aireport
 * @since 1.0.0
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    FAIL(500, "操作失败"),

    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 请求方法不支持
     */
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),

    /**
     * 请求超时
     */
    REQUEST_TIMEOUT(408, "请求超时"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统异常"),

    /**
     * 业务异常
     */
    BUSINESS_ERROR(501, "业务异常"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(600, "文件上传失败"),

    /**
     * 文件格式错误
     */
    FILE_FORMAT_ERROR(601, "文件格式错误"),

    /**
     * AI服务异常
     */
    AI_SERVICE_ERROR(700, "AI服务异常"),

    /**
     * AI调用超时
     */
    AI_TIMEOUT(701, "AI调用超时");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 返回消息
     */
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}