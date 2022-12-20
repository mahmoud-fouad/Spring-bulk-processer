package com.mfouad.batchPro.repos;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mfouad.batchPro.entities.Transfer;
import com.mfouad.batchPro.entities.TransferStatus;

@Repository
public interface TransferRepo extends JpaRepository<Transfer, Long> {
	
	@Query("select t from Transfer t where  t.executionDate >= :exDate and t.status = :status")
	Page<Transfer> getTransferByExecutionDate(@Param("exDate") Date date,@Param("status")TransferStatus status,  Pageable pageable );
	
	@Query("select t from Transfer t where  t.status = :status")
	Page<Transfer> getLimitCheckPassedTransfers(@Param("status")TransferStatus status,  Pageable pageable );
	
}
