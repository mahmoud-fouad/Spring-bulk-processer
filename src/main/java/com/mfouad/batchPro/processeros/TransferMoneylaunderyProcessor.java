package com.mfouad.batchPro.processeros;

import org.springframework.batch.item.ItemProcessor;

import com.mfouad.batchPro.entities.Transfer;
import com.mfouad.batchPro.entities.TransferStatus;
import com.mfouad.batchPro.services.IValidator;
import com.mfouad.batchPro.services.MoneyLaunderyCheck;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferMoneylaunderyProcessor implements ItemProcessor<Transfer, Transfer> {

	@Override
	public Transfer process(Transfer transfer) throws Exception {
		log.info("in " + TransferLimitCheckProcessor.class + " -----------------");
		log.info("process transfer {}", transfer);

		IValidator check = new MoneyLaunderyCheck();
		if (!check.valid(transfer))
			transfer.setStatus(TransferStatus.suspected);

		return transfer;
	}

}
