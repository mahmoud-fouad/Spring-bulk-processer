package com.mfouad.batchPro.processeros;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.mfouad.batchPro.SchedularConfig;
import com.mfouad.batchPro.entities.Transfer;
import com.mfouad.batchPro.entities.TransferStatus;
import com.mfouad.batchPro.listeners.CustomChunkListener;
import com.mfouad.batchPro.listeners.JobCompletionListener;
import com.mfouad.batchPro.listeners.TransferJobCompletionListener;
import com.mfouad.batchPro.repos.TransferRepo;
import com.mfouad.batchPro.services.ITransferService;


@Component
@EnableBatchProcessing
public class TransferExecutionBatchConfigurations {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	TransferRepo transferRepo;

	@Autowired
	private SchedularConfig schedularConfig;

	@Bean
	public Job processJob(JobCompletionListener listener,@Qualifier("checkTransferLimit") Step checkTransferLimit 
			, @Qualifier("checkTransferMoneyLundary") Step checkTransferMoneyLundary
			, @Qualifier("transferSubmission") Step transferSubmission ) throws Exception {

		return this.jobBuilderFactory.get("processJob").incrementer(new RunIdIncrementer()).listener(listener)
				.start(checkTransferLimit).next(checkTransferMoneyLundary).next(transferSubmission).build();
	}
	
	
	//https://stackoverflow.com/questions/53490144/spring-batch-understanding-chunk-processing
		@Bean(name="checkTransferLimit")
		public Step checkTransferLimit(ItemReader<Transfer> checkTransferLimitReader) {
				return stepBuilderFactory.get("checkTransferLimit")
						.<Transfer, Transfer> chunk(schedularConfig.getChunkSize())				
						.reader(checkTransferLimitReader)
						.processor(TransferLimitCheckProcessor())
						.writer(transferLimitWriter())					
						.build();
		}
		
		@Bean(name="checkTransferMoneyLundary")
		public Step checkTransferMoneyLundary(ItemReader<Transfer> checkTransferMoneyLundaryReader) {
				return stepBuilderFactory.get("checkTransferMoneyLundary")
						.<Transfer, Transfer> chunk(schedularConfig.getChunkSize())				
						.reader(checkTransferMoneyLundaryReader)
						.processor(TransferMoneylaunderyProcessor())
						.writer(transferMoneyLauderyWriter())					
						.build();
		}
		
		@Bean(name="transferSubmission")
		public Step transferSubmission(ItemReader<Transfer> transferSubmissionReader) {
				return stepBuilderFactory.get("transferSubmission")
						.<Transfer, Transfer> chunk(schedularConfig.getChunkSize())				
						.reader(transferSubmissionReader)
						.processor(transferSubmittionProcessor())
						.writer(transferLimitWriter())					
						.build();
		}
		
		// You should sepcify the method which  
	    //spring batch should call in your repository to fetch 
	    // data and the arguments it needs needs to be  
	    //specified with the below method.
		@StepScope
		@Bean(name="checkTransferLimitReader")
	    public RepositoryItemReader<Transfer> transferLimitReader(@Value("#{jobParameters['exDate']}") Date exDate) {
	        RepositoryItemReader<Transfer> reader = new RepositoryItemReader<>();
	        
	        reader.setRepository(transferRepo);
	        reader.setMethodName("getTransferByExecutionDate");
	      
	        List<Object> queryMethodArguments = new ArrayList<>();
	        queryMethodArguments.add(exDate);
	        queryMethodArguments.add(TransferStatus.scheduled);
	        reader.setArguments(queryMethodArguments);
	        
	        reader.setPageSize(schedularConfig.getPageSize());
	        Map<String, Direction> sorts = new HashMap<>();        
	        sorts.put("id", Direction.ASC);//https://stackoverflow.com/questions/31691470/ora-01791-not-a-selected-expression
	        reader.setSort(sorts);

	        return reader;
	    }
		
		@StepScope
		@Bean(name="checkTransferMoneyLundaryReader")
	    public RepositoryItemReader<Transfer> transferMoneyLaunderyReader(@Value("#{jobParameters['exDate']}") Date exDate) {
	        RepositoryItemReader<Transfer> reader = new RepositoryItemReader<>();
	        
	        reader.setRepository(transferRepo);
	        reader.setMethodName("getTransferByExecutionDate");
	      
	        List<Object> queryMethodArguments = new ArrayList<>();
	        queryMethodArguments.add(exDate);
	        queryMethodArguments.add(TransferStatus.limitCheckPassed);
	        reader.setArguments(queryMethodArguments);
	        
	        reader.setPageSize(schedularConfig.getPageSize());
	        Map<String, Direction> sorts = new HashMap<>();        
	        sorts.put("id", Direction.ASC);//https://stackoverflow.com/questions/31691470/ora-01791-not-a-selected-expression
	        reader.setSort(sorts);

	        return reader;
	    }
		
		@StepScope
		@Bean(name="transferSubmissionReader")
	    public RepositoryItemReader<Transfer> transferSubmissionReader() {
	        RepositoryItemReader<Transfer> reader = new RepositoryItemReader<>();
	        
	        reader.setRepository(transferRepo);
	        reader.setMethodName("getLimitCheckPassedTransfers");
	      
	        List<Object> queryMethodArguments = new ArrayList<>();
	        queryMethodArguments.add(TransferStatus.limitCheckPassed);
	        reader.setArguments(queryMethodArguments);
	        
	        reader.setPageSize(schedularConfig.getPageSize());
	        Map<String, Direction> sorts = new HashMap<>();        
	        sorts.put("id", Direction.ASC);//https://stackoverflow.com/questions/31691470/ora-01791-not-a-selected-expression
	        reader.setSort(sorts);

	        return reader;
	    }
		 
		@Bean
	    public TransferLimitCheckProcessor TransferLimitCheckProcessor()
	    {
	        return new TransferLimitCheckProcessor();
	    }
		
		@Bean(name="TransferMoneylaunderyProcessor")
	    public TransferMoneylaunderyProcessor TransferMoneylaunderyProcessor()
	    {
	        return new TransferMoneylaunderyProcessor();
	    }
		
		@Autowired
		ITransferService transferService;
		
		@Bean(name="transferSubmittionProcessor")
	    public TransferSubmittionProcessor transferSubmittionProcessor()
	    {
	        return new TransferSubmittionProcessor(transferService);
	    }
		
		@Bean
	    public ItemWriter<Transfer> transferLimitWriter() {       
			return new TransferLimitCheckWriter(transferRepo,"save");
	    }
		
		
		@Bean
	    public ItemWriter<Transfer> transferMoneyLauderyWriter() {   
			return new TransferMoneylaunderyWriter();
	    }
		@Bean
		public JobExecutionListener listener() {
			return new TransferJobCompletionListener();
		}
//		@Bean
//	    public RepositoryItemWriter<Transfer> repositoryItemWriter() {
//	        RepositoryItemWriter<Transfer> iwriter = new RepositoryItemWriter<>();       
//	        iwriter.setRepository(transferRepo);
//	        iwriter.setMethodName("save");
//	        return iwriter;
//			
//	    }

}
