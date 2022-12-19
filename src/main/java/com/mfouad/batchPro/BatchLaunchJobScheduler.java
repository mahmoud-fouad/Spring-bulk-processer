package com.mfouad.batchPro;

import java.text.ParseException;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mfouad.batchPro.utils.DateUtils;


@Component
/**
 * 
 * @author mahmoud
 *the Schedule component to launch the transfer job
 */
public class BatchLaunchJobScheduler {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job processJob;

	@Scheduled(fixedDelayString = "${sechdule.interval}")
	public void schedule() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException, ParseException {

		JobParameters jobParameters = new JobParametersBuilder()
				.addDate("exDate", DateUtils.setStartDate(new Date()))

				.toJobParameters();
		jobLauncher.run(processJob, jobParameters);
	}

}
