package com.mfouad.batchPro.processeros;

import org.springframework.batch.item.ItemProcessor;

import com.mfouad.batchPro.entities.Transfer;
import com.mfouad.batchPro.entities.TransferStatus;
import com.mfouad.batchPro.services.IValidator;
import com.mfouad.batchPro.services.LimitService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferLimitCheckProcessor implements ItemProcessor<Transfer, Transfer>{

	@Override
	public Transfer process(Transfer transfer) throws Exception {
		log.info("in "+TransferLimitCheckProcessor.class+" -----------------");
		
		IValidator limitCheck = new LimitService();
		if(limitCheck.valid(transfer))
		transfer.setStatus(TransferStatus.limitCheckPassed) ;
		else
			transfer.setStatus(TransferStatus.limitCheckFailed);
		log.info("process transfer {}",transfer);
		
		return transfer;
	}

}
