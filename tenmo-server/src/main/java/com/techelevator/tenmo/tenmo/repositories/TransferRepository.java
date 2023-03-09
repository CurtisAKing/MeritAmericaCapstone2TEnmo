package com.techelevator.tenmo.tenmo.repositories;

import com.techelevator.tenmo.tenmo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//database interaction interface for transfer
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {


	List<Transfer> findAllByAccountFromOrAccountTo(int accountFrom, int accountTo);

	List<Transfer> findAllByAccountFromAndTransferStatusId(int accountFrom, int transferStatusId);

	List<Transfer> findAllByAccountToAndTransferStatusId(int accountTo, int transferStatusId);

	List<Transfer> findAllByTransferStatusIdAndTransferTypeIdAndAccountFrom(int transferStatus, int transferType, int accountFrom);

	List<Transfer> findAllByTransferStatusIdAndTransferTypeIdAndAccountTo(int transferStatus, int transferType, int accountTo);

	Transfer findByTransferId(int transferId);

	Transfer findByAccountFromAndTransferId(int accountId, int transferId);

	Transfer findAllByAccountFromAndTransferId(int accountId, int transferId);
}
