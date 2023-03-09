package com.techelevator.tenmo.model;

public class TransferStatus {

    private int transferStatus;
    private String transferStatusDescription;

    public TransferStatus(int transferStatus, String transferStatusDescription) {
        this.transferStatus = transferStatus;
        this.transferStatusDescription = transferStatusDescription;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getTransferStatusDescription() {
        return transferStatusDescription;
    }

    public void setTransferStatusDescription(String transferStatusDescription) {
        this.transferStatusDescription = transferStatusDescription;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "TransferStatus{" +
                "transferStatus=" + transferStatus +
                ", transferStatusDescription='" + transferStatusDescription + '\'' +
                '}';
    }
}
