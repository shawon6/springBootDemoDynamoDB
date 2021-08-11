package com.shawon.demo.marchent.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
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
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS("1"));

		DynamoDBQueryExpression<Marchent> queryExpression = new DynamoDBQueryExpression<Marchent>()
		    .withKeyConditionExpression("id = :v1")
		    .withExpressionAttributeValues(eav);

		List<Marchent> marchentList = dynamoDBMapper.query(Marchent.class, queryExpression);
		return marchentList;
	} 
}
