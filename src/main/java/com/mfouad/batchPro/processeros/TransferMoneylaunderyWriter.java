package com.mfouad.batchPro.processeros;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.mfouad.batchPro.entities.Transfer;
import com.mfouad.batchPro.entities.TransferStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferMoneylaunderyWriter extends FlatFileItemWriter<Transfer> {
	
	
	public TransferMoneylaunderyWriter() {
		super();
		Resource outputResource = new FileSystemResource("output//moneyLauderyTransfers.csv");
		setResource(outputResource);
		setAppendAllowed(true);
		
		setLineAggregator(new DelimitedLineAggregator<Transfer>() {
		      {
		        setDelimiter(",");
		        setFieldExtractor(new BeanWrapperFieldExtractor<Transfer>() {
		          {
		            setNames(new String[] { "fromAccount", "toAccount","amount","type" });
		          }
		        });
		      }
		    });
	}
	
	
	@Override
		public void write(List<? extends Transfer> transfers) throws Exception {
		log.info("in {}---------------------",TransferMoneylaunderyWriter.class);
			List<Transfer> moneyLaudery = transfers.stream().filter((transfer) ->transfer.getStatus()== TransferStatus.suspected ).collect(Collectors.toList());
			
			log.info("money laundary transfer list size to be writtern is {}" ,moneyLaudery.size());
			
			super.write(moneyLaudery);
		}
	

}
