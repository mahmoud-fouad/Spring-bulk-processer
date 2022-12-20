package com.mfouad.batchPro.processeros;

import java.util.List;

import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.CrudRepository;

import com.mfouad.batchPro.entities.Transfer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferSumbitionWriter extends RepositoryItemWriter<Transfer> {

	public TransferSumbitionWriter() {
		super();
	}
	TransferSumbitionWriter(CrudRepository<Transfer,Long> rep , String method){
		super();
		setRepository(rep);
		setMethodName(method);
	}
	
	@Override
	public void write(List<? extends Transfer> transfers) throws Exception {
		log.info("in "+this.getClass()+".write------------------------");
		
		transfers.forEach(transfer -> log.info("transfer after processing {}", transfer) );
		
	}

}
