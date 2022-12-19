package com.mfouad.batchPro.services;

public interface IlimitService extends IValidator{

	double getOwnLimit();
	double getWithinimit();
	double getLocalLimit();
	double getinternationalLimit();
	
}
