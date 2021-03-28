package com.example.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class ExpenseModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String expenseId;
    private Integer billNumber;
    private Integer billCost;
    private LocalDate datedOn;
    private String status;
    private String remark;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(referencedColumnName = "email")
    private UserModel claimedBy;

    public ExpenseModel() {
    }

    public ExpenseModel(Integer billNumber, Integer billCost, LocalDate datedOn, String status, String remark, UserModel claimedBy) {
        this.billNumber = billNumber;
        this.billCost = billCost;
        this.datedOn = datedOn;
        this.status = status;
        this.remark = remark;
        this.claimedBy = claimedBy;
    }

    public ExpenseModel(String expenseId, Integer billNumber, Integer billCost, LocalDate datedOn, String status, String remark, UserModel claimedBy) {
        this.expenseId = expenseId;
        this.billNumber = billNumber;
        this.billCost = billCost;
        this.datedOn = datedOn;
        this.status = status;
        this.remark = remark;
        this.claimedBy = claimedBy;
    }

    public Integer getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(Integer billNumber) {
        this.billNumber = billNumber;
    }

    public Integer getBillCost() {
        return billCost;
    }

    public void setBillCost(Integer billCost) {
        this.billCost = billCost;
    }

    public LocalDate getDatedOn() {
        return datedOn;
    }

    public void setDatedOn(LocalDate datedOn) {
        this.datedOn = datedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserModel getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(UserModel claimedBy) {
        this.claimedBy = claimedBy;
    }

    public String getExpenseId() {
        return expenseId;
    }

    @Override
    public String toString() {
        return "ExpenseModel{" +
                "expenseId='" + expenseId + '\'' +
                ", billNumber=" + billNumber +
                ", billCost=" + billCost +
                ", datedOn=" + datedOn +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", claimedBy=" + claimedBy +
                '}';
    }
}
