package com.app.service;

import java.util.List;

import com.app.pojos.Batch;
public interface IBatchService {
//add a method to add batch
	Batch addBatch(Batch b,int trainerId,int branchId);
	
//add a method to delete batch
	String deleteBatch(int batchId);
	

// add a method to get all Trainer
	List<Batch> getAllBatch();	
	
	
	
	//add a method to get batch by id
	Batch getBatchById(int batchId);
}
