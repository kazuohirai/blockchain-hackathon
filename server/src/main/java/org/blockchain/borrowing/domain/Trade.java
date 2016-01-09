package org.blockchain.borrowing.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * 交易记录
 * <p>
 * Created by pengchangguo on 16/1/9.
 */
@Entity
@Table(name = "b_trade")
public class Trade {

    /**
     * 交易编号
     */
    @Id
    @Column(name = "trade_no")
    private String tradeNo = UUID.randomUUID().toString();

    /**
     * 借款区块Id
     */
    @Column(name = "borrower_hash")
    private String borrowerHash;

    /**
     * 还款区块Id
     */
    @Column(name = "lender_hash")
    private String lenderHash;


    /**
     * 借款人
     */
    @Column(name = "borrower")
    private long borrower;

    /**
     * 放款人
     */
    @Column(name = "lender")
    private Long lender;

    /**
     * 金额
     */
    @Column(name = "amount")
    private double amount;

    /**
     * 利息
     */
    @Column(name = "interest")
    private double interest;

    /**
     * 状态
     */
    @Column(name = "status")
    private Status status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime = new Date();

    /**
     * 最近修改时间
     */
    @Column(name = "last_time")
    private Date lastTime = new Date();


    public enum Status {

        /* 初始状态 */
        INIT,

        /* 进行中(借款成功) */
        ING,

        /* 完成(还款成功) */
        COM,

        /* 已失效 */
        DON
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getBorrowerHash() {
        return borrowerHash;
    }

    public void setBorrowerHash(String borrowerHash) {
        this.borrowerHash = borrowerHash;
    }

    public String getLenderHash() {
        return lenderHash;
    }

    public void setLenderHash(String lenderHash) {
        this.lenderHash = lenderHash;
    }

    public long getBorrower() {
        return borrower;
    }

    public void setBorrower(long borrower) {
        this.borrower = borrower;
    }

    public Long getLender() {
        return lender;
    }

    public void setLender(Long lender) {
        this.lender = lender;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public static Trade sampleValue() {
        Trade trade = new Trade();
        trade.borrowerHash = "borrowerHash";
        trade.lenderHash = "lenderHash";
        trade.borrower = 1L;
        trade.lender = 2L;
        trade.amount = 1000L;
        trade.interest = 10L;
        trade.status = Status.INIT;

        return trade;
    }
}
