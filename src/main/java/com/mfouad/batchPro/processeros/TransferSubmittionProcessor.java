package com.mfouad.batchPro.processeros;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.mfouad.batchPro.entities.Transfer;
import com.mfouad.batchPro.services.ITransferService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author mahmoud
 * 3rd step processor to submit the transfer and retrieve its status
 */
@Slf4j
public class TransferSubmittionProcessor implements ItemProcessor<Transfer,Transfer> {

	ITransferService transferService;
	
	public TransferSubmittionProcessor(ITransferService transferService) {
		this.transferService=transferService;
	}
	
	@Override
	public Transfer process(Transfer transfer) throws Exception {
		log.info("in {} ------------------",TransferSubmittionProcessor.class);
		log.info("begin process transfer {}",transfer);
		transferService.process(transfer);
		return transfer;
	}


}
