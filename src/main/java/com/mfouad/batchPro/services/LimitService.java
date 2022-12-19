package com.mfouad.batchPro.services;

import com.mfouad.batchPro.entities.Transfer;

/**
 * 
 * @author mahmoud
 *
 *Class to validate the transfer doesn't exceed limit 
 */
public class LimitService implements IlimitService{

	@Override
	public boolean valid(Transfer transfer) {
		switch (transfer.getType()) {
		case own:
			
			return transfer.getAmount() <=getOwnLimit();

		case within:
			
			return transfer.getAmount() <=getLocalLimit();

		case local:
			
			return transfer.getAmount() <=getOwnLimit();

		case international:
			
			return transfer.getAmount() <=getOwnLimit();

		default:
			break;
		}
		
		return false;
	}

	@Override
	public double getOwnLimit() {
		return 50000;
	}

	@Override
	public double getWithinimit() {
		return 50000;
	}

	@Override
	public double getLocalLimit() {
		return 25000;
	}

	@Override
	public double getinternationalLimit() {
		return 10000;
	}

}
