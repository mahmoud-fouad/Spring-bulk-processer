package com.mfouad.batchPro.listeners;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobCompletionListener implements JobExecutionListener {

	@Override
	public void afterJob(JobExecution arg0) {
		
		log.info("job id {} with name {} finieshed at {} with status ",arg0.getId(),arg0.getJobConfigurationName(),arg0.getEndTime(),arg0.getStatus().name());
		
	}

	@Override
	public void beforeJob(JobExecution arg0) {
		log.info("job id {} with name {} will begin at {}",arg0.getId(),arg0.getJobConfigurationName(),new Date());
	}

}
