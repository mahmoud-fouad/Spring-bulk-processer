package com.mfouad.batchPro.services;

import com.mfouad.batchPro.entities.Transfer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyLaunderyCheck implements IValidator {

	@Override
	// mock 
	//if to account ends with money this is money Laundery process 
	public boolean valid(Transfer transfer) {
		log.info("in {} --------------------",MoneyLaunderyCheck.class);
		if( transfer.getToAccount().endsWith("money")){
			log.info("found money laundery case from transfer {}",transfer);
			return false;
		}
		else return true;
		
	}

}
