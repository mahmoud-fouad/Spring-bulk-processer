package com.mfouad.batchPro.services;

import org.springframework.stereotype.Service;

import com.mfouad.batchPro.entities.Transfer;
import com.mfouad.batchPro.entities.TransferStatus;

/**
 * 
 * @author mahmoud
 * simulate transfer processing
 */
@Service
public class TransferService implements ITransferService {

	@Override
	public void process(Transfer transfer) {
		transfer.setStatus(TransferStatus.success);

	}

}
