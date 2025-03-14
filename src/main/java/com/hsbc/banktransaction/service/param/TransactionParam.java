package com.hsbc.banktransaction.service.param;

import com.hsbc.banktransaction.domain.type.TransactionTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionParam {
    /**
     * 交易ID
     */
    @NotNull(message = "交易ID不能为空")
    private Long transactionId;
    /**
     * 客户账号
     */
    @NotEmpty(message = "客户账号不能为空")
    private String cstAccno;
    /**
     * 交易对手方账号
     */
    @NotEmpty(message = "交易对手方账号不能为空")
    private String counterAccno;
    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不能为空")
    private Integer type;

    /**
     * 金额
     */
    @NotNull(message = "交易金额不能为空")
    @Positive(message = "交易金额必须大于零")
    private BigDecimal amount;

    /**
     * 描述
     */
    private String desc;
}
