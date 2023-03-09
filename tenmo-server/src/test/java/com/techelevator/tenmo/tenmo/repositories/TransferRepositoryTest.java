package com.techelevator.tenmo.tenmo.repositories;

import com.techelevator.tenmo.tenmo.model.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransferRepositoryTest {

	@Autowired
	TransferRepository transferRepository;

	@BeforeEach
	public void setup(){
		Transfer transfer1 = Transfer.builder()
				.transferId(2200)
				.transferTypeId(1)
				.transferStatusId(1)
				.accountFrom(2002)
				.accountTo(2003)
				.amount(BigDecimal.valueOf(125.00))
				.build();
		transferRepository.save(transfer1);

		Transfer transfer2 = Transfer.builder()
				.transferId(2201)
				.transferTypeId(1)
				.transferStatusId(2)
				.accountFrom(2002)
				.accountTo(2003)
				.amount(BigDecimal.valueOf(150.00))
				.build();
		transferRepository.save(transfer2);

		Transfer transfer3 = Transfer.builder()
				.transferId(2202)
				.transferTypeId(2)
				.transferStatusId(3)
				.accountFrom(2225)
				.accountTo(2003)
				.amount(BigDecimal.valueOf(175.00))
				.build();
		transferRepository.save(transfer3);


	}

	@Test
	public void findAllByAccountFromOrAccountTwoShouldReturn2(){
		List<Transfer> transfers = transferRepository.findAllByAccountFromOrAccountTo(2002,2002);
		assertThat(transfers).hasSize(2);
	}

	@Test
	public void findAllByAccountFromAndTransferStatusId1ShouldReturn1(){
		List<Transfer> transfers = transferRepository.findAllByAccountFromAndTransferStatusId(2002,1);
		assertThat(transfers).hasSize(1);
	}

	@Test
	public void findAllByAccountToAndTransferStatusIdShouldReturn1(){
		List<Transfer> transfers = transferRepository.findAllByAccountToAndTransferStatusId(2003,1);
		assertThat(transfers).hasSize(1);
	}

}
