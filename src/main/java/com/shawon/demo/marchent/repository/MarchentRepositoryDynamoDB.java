package com.shawon.demo.marchent.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.shawon.demo.marchent.entity.Marchent;

@Repository
public class MarchentRepositoryDynamoDB {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	public void save(Marchent marchent) {
		dynamoDBMapper.save(marchent);
	} 
	
	public Marchent findById(Marchent marchent) {
		return dynamoDBMapper.load(Marchent.class, marchent.getId(), marchent.getName());
	} 
	
	public Marchent delete(Marchent model) {
		Marchent m = findById(model);
		dynamoDBMapper.delete(m);
		return m;
	} 
	
	public List<Marchent> getAllMarchent () {
		return null; //dynamoDBMapper.batchLoad(itemsToGet, config);
	} 
}
