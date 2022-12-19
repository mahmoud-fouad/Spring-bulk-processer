package com.mfouad.batchPro.services;

import com.mfouad.batchPro.entities.Transfer;

/**
 * 
 * @author mahmoud
 * Validation chain interface
 */
public interface IValidator {

	boolean valid(Transfer transfer);
	
}
