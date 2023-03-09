package com.techelevator.tenmo.model;

public class TransferType {

    private int transferTypeId;
    private String typeDescription;

    public TransferType(int transferTypeId, String typeDescription) {
        this.transferTypeId = transferTypeId;
        this.typeDescription = typeDescription;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "TransferType{" +
                "transferTypeId=" + transferTypeId +
                ", typeDescription='" + typeDescription + '\'' +
                '}';
    }
}
