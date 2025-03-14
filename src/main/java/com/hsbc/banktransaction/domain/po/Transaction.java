package com.hsbc.banktransaction.domain.po;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name ="transaction",indexes = {
        @Index(name = "uk_transaction_id", columnList = "transaction_id", unique = true),
        @Index(name = "idx_update_time_desc", columnList = "update_time DESC")})







public class Transaction {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 交易ID
     */
    @Column(name = "transaction_id")
    private Long transactionId;
    /**
     * 客户账号
     */
    @Column(name = "cst_accno")
    private String cstAccno;
    /**
     * 交易对手方账号
     */
    @Column(name = "counter_accno")
    private String counterAccno;
    /**
     * 交易金额
     */
    @Column(name = "amount")
    private BigDecimal amount;
    /**
     * 交易类型
     * 11:转入
     * 12:转出
     * 13:快捷支付
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 描述
     */
    @Column(name = "desc")
    private String desc;
    /**
     * 创建人
     */
    @Column(name = "creator")
    private String creator;
    /**
     *  创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @Column(name = "updator")
    private String updator;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    // Getters and Setters
}