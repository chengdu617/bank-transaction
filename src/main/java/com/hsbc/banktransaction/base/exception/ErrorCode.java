package com.hsbc.banktransaction.base.exception;

/**
 * 错误码
 *
 */
public interface ErrorCode {
    /**
     * 错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
