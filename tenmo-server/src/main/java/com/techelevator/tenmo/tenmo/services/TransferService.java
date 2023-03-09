package com.techelevator.tenmo.tenmo.services;

import com.techelevator.tenmo.tenmo.model.Transfer;
import com.techelevator.tenmo.tenmo.pojos.TransferRequest;
import com.techelevator.tenmo.tenmo.repositories.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Carries the workload of the TransferController
@Service
public class TransferService {

	TransferRepository transferRepository;

	public TransferService(TransferRepository transferRepository) {
		this.transferRepository = transferRepository;
	}


	public List<Transfer> getHistory(int accountId) {
		return transferRepository.findAllByAccountFromOrAccountTo(accountId, accountId);
	}

	public Transfer saveTransfer(TransferRequest transferRequest) {
		Transfer transfer = new Transfer();
		transfer.setAccountFrom(transferRequest.accountFrom);
		transfer.setAccountTo(transferRequest.accountTo);
		transfer.setTransferTypeId(transferRequest.transferTypeId);
		transfer.setTransferStatusId(transferRequest.transferStatusId);
		transfer.setAmount(transferRequest.amount);

		return transferRepository.save(transfer);
	}

	public List<Transfer> getPendingFrom(int accountFrom) {
		return transferRepository.findAllByAccountFromAndTransferStatusId(accountFrom, 1);
	}

	public List<Transfer> getPendingTo(int accountTo) {
		return transferRepository.findAllByAccountToAndTransferStatusId(accountTo, 1);
	}

	public Transfer updateTransfer(Transfer transfer) {
		return transferRepository.save(transfer);
	}

	public Transfer getTransfer(int transferId){
		return transferRepository.findByTransferId(transferId);
	}

	public List<Transfer> getPendingOutgoing(int accountFrom){
		return transferRepository
				.findAllByTransferStatusIdAndTransferTypeIdAndAccountFrom(1, 1, accountFrom);
	}

	public List<Transfer> getPendingIncoming(int accountTo){
		return transferRepository
				.findAllByTransferStatusIdAndTransferTypeIdAndAccountTo(1, 1, accountTo);

	}

	public Transfer getTransferFromAccount(int accountId, int transferId) {
		return transferRepository
				.findAllByAccountFromAndTransferId(accountId, transferId);
	}
}
