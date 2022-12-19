package com.mfouad.batchPro.processeros;

import java.util.List;

import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.CrudRepository;

import com.mfouad.batchPro.entities.Transfer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferLimitCheckWriter extends RepositoryItemWriter<Transfer> {

	public TransferLimitCheckWriter() {
		// TODO Auto-generated constructor stub
	}
	TransferLimitCheckWriter(CrudRepository rep , String method){
		setRepository(rep);
		setMethodName(method);
	}
	
	@Override
	public void write(List<? extends Transfer> transfers) throws Exception {
		log.info("in "+this.getClass()+".wite------------------------");
		
		transfers.forEach(transfer -> log.info("transfer after processing {}", transfer) );
		
	}

}
