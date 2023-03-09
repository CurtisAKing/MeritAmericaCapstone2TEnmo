package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;

    public Transfer(int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount) {
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer() {
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public java.lang.String toString() {

        String transferTypeIdWords;
        String transferStatusIdWords;

        if (transferTypeId == 1){
            transferTypeIdWords = "Request";
        } else {
            transferTypeIdWords = "Send";
        }

        if (transferStatusId == 1){
            transferStatusIdWords = "Pending";
        } else if (transferStatusId == 2) {
            transferStatusIdWords = "Approved";
        } else {
            transferStatusIdWords = "Rejected";
        }

        return  "\nTransfer ID: " + transferId +
                "\nTransfer Type Id: " + transferTypeIdWords +
                "\nTransfer Status Id: " + transferStatusIdWords +
                "\nAccount From: " + accountFrom +
                "\nAccount To: " + accountTo +
                "\nAmount: " + amount;
    }
}
