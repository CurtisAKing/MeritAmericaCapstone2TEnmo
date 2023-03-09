package com.techelevator.tenmo.tenmo.pojos;

import java.math.BigDecimal;
 // pojo without transferId used to save new transfers
public class TransferRequest {

	public int transferTypeId;

	public int transferStatusId;

	public int accountFrom;

	public int accountTo;

	public BigDecimal amount;

}
