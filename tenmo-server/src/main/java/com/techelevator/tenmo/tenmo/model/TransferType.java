package com.techelevator.tenmo.tenmo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transfer_type")
public class TransferType {

    @Id
    @Column(name = "transfer_type_id")
    @NotNull
    private int transferTypeId;

    @Column(name = "transfer_type_desc")
    @NotNull
    private String typeDescription;

    public TransferType(int transferTypeId, String typeDescription) {
        this.transferTypeId = transferTypeId;
        this.typeDescription = typeDescription;
    }

    public TransferType() {
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

    @Override
    public String toString() {
        return "TransferType{" +
                "transferTypeId=" + transferTypeId +
                ", typeDescription='" + typeDescription + '\'' +
                '}';
    }
}