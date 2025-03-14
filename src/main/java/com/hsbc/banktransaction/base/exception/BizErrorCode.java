
package com.hsbc.banktransaction.base.exception;

/**
 * 业务通用错误码
 *
 */
public enum BizErrorCode implements ErrorCode {


    TRANSACTION_DUPLICATE(401, "创建失败，交易已存在"),
    TRANSACTION_DELETE_NOT_EXIST(402, "删除失败，交易不存在"),
    TRANSACTION_UPDATE_NOT_EXIST(403, "更新失败，交易不存在"),;




    private int code;


    private String message;

    BizErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
