package com.hsbc.banktransaction.domain.type;




public enum TransactionTypeEnum {
    RECEIVE(11,"转入"),
    DEDUCT(12,"转出"),
    PAY(13,"快捷支付");;

    private final int type;
    private final String description;

    TransactionTypeEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
