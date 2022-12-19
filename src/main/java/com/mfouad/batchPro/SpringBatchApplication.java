package com.mfouad.batchPro;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mfouad.batchPro.entities.Transfer;
import com.mfouad.batchPro.entities.TransferStatus;
import com.mfouad.batchPro.entities.TransferType;
import com.mfouad.batchPro.repos.TransferRepo;

@SpringBootApplication
@EnableScheduling
public class SpringBatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

	@Autowired
	TransferRepo transferRepo; 
	
	@Override
	public void run(String... args) throws Exception {
		
		transferRepo.save(
		Transfer.builder().amount(1000).fromAccount("10000").toAccount("1556_money").executionDate(new Date()).status(TransferStatus.scheduled).type(TransferType.international).build()
		);
		
		transferRepo.save(
				Transfer.builder().amount(6000).fromAccount("24234").toAccount("1558676").executionDate(new Date()).status(TransferStatus.scheduled).type(TransferType.within).build()
				);
		
		transferRepo.save(
				Transfer.builder().amount(300).fromAccount("4547").toAccount("1523656").executionDate(new Date()).status(TransferStatus.scheduled).type(TransferType.own).build()
				);
		
		transferRepo.save(
				Transfer.builder().amount(20000).fromAccount("123").toAccount("3457").executionDate(new Date()).status(TransferStatus.scheduled).type(TransferType.local).build()
				);
		
		transferRepo.save(
				Transfer.builder().amount(10000).fromAccount("87923").toAccount("2358").executionDate(new Date()).status(TransferStatus.success).type(TransferType.local).build()
				);
		
		transferRepo.save(
				Transfer.builder().amount(10300).fromAccount("156j8").toAccount("23425").executionDate(new Date()).type(TransferType.international).status(TransferStatus.failed).build()
				);
		
	}

}
