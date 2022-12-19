# Spring-bulk-processer
this is spring boot appliation using spring boot batch processing to simulate scheduled transfers submittion
the appliation has Scheduler to fire the job
the pocessing used 2 steps
1st check on scheduled transfers limited to be executed at that day
2nd to suspend  money Laundery transfers and save their data in csv
3rd to process the transfer and retieve updated status

The processing reading using jpaRepository 
and uses 2 writers RepositoryItemWriter and FlatFileItemWriter
