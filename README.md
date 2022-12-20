# Spring-bulk-processer
this is spring boot appliation using spring boot batch processing to simulate scheduled transfers submittion
the appliation has Scheduler to fire the job
the pocessing used 3 steps
1st check on scheduled transfers limited to be executed at that day
2nd to suspend  money Laundery transfers and save the suspected transfer details in csv
3rd to process the transfer and retieves updated status

The processing reading using jpaRepository 
and uses 2 writers RepositoryItemWriter and FlatFileItemWriter

**com.mfouad.batchPro.BatchLaunchJobScheduler ** the scheduled task which fire the job
**com.mfouad.batchPro.entities** entities package
**com.mfouad.batchPro.processeros** the job processor and step package
**com.mfouad.batchPro.services** the service package which contains the limit  , money Laudnery checks and transfer sumbittion services
**com.mfouad.batchPro.utils** utils package

To run the application
we need to run com.mfouad.batchPro.SpringBatchApplication
its implement CommandLineRunner to add test data in the h2-database


application startes on port 8080 and
Note: there is no apis or ui 



