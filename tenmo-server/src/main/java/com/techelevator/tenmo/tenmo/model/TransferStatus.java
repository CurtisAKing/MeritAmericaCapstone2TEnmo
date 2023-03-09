package com.techelevator.tenmo.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "transfer_status")
public class TransferStatus {

    @Id
    @Column(name = "transfer_status_id")
    @NotNull
    private int transferStatus;

    @Column(name = "transfer_status_desc")
    @NotNull
    private String transferStatusDescription;

    public TransferStatus(int transferStatus, String transferStatusDescription) {
        this.transferStatus = transferStatus;
        this.transferStatusDescription = transferStatusDescription;
    }

    public TransferStatus() {
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

    @Override
    public String toString() {
        return "TransferStatus{" +
                "transferStatus=" + transferStatus +
                ", transferStatusDescription='" + transferStatusDescription + '\'' +
                '}';
    }
}