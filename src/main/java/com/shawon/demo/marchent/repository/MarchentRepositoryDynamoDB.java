package com.shawon.demo.marchent.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.shawon.demo.marchent.entity.Marchent;
import com.shawon.demo.marchent.exception.DynamoDbException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MarchentRepositoryDynamoDB {

	public static final String DYNAMO_ERROR = "DYNAMO_ERROR";
	
	@Autowired
	private DynamoDBMapper dynamoDBMapper;
	
	public void save(Marchent marchent) {
		dynamoDBMapper.save(marchent);
	} 
	
	public Marchent findByKey(Marchent marchent) {
		return dynamoDBMapper.load(Marchent.class, marchent.getId(), marchent.getName());
	} 
	
	public Marchent delete(Marchent model) {
		Marchent m = findByKey(model);
		dynamoDBMapper.delete(m);
		return m;
	} 
	
	public List<Marchent> getUsingQuery () {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS("1"));
		//eav.put(":v2", new AttributeValue().withS("01948"));

		DynamoDBQueryExpression<Marchent> queryExpression = new DynamoDBQueryExpression<Marchent>()
		    .withKeyConditionExpression("id = :v1")
		    //.withFilterExpression("number = :v2")
		    .withExpressionAttributeValues(eav);

		List<Marchent> marchentList = dynamoDBMapper.query(Marchent.class, queryExpression);
		return marchentList;
	}

	public Mono<Boolean> addMarchentUsingWebFlux(Marchent model) {
		try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> dynamoDBMapper.save(model));

            return Mono.fromCompletionStage(future)
                    .then(Mono.just(true))
                    .onErrorMap(throwable -> new DynamoDbException(DYNAMO_ERROR, throwable));
        } catch (Exception ex) {
        	ex.printStackTrace();
            return Mono.error(ex);
        }
	}
	
	public Mono<Boolean> deleteMarchentUsingWebFlux(Marchent model) {
		try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> dynamoDBMapper.delete(model));

            return Mono.fromCompletionStage(future)
                    .then(Mono.just(true))
                    .onErrorMap(throwable -> new DynamoDbException(DYNAMO_ERROR, throwable));
        } catch (Exception ex) {
        	ex.printStackTrace();
            return Mono.error(ex);
        }
	}
	
	public  Mono<Marchent> getMarchentByIdUsingWebFlux(Marchent model) {
        try {
            CompletableFuture<Marchent> future =
                    CompletableFuture.supplyAsync(() -> dynamoDBMapper.load(Marchent.class, model.getId(), model.getName()));

            return Mono.fromCompletionStage(future)
                    .onErrorMap(throwable -> new DynamoDbException(DYNAMO_ERROR, throwable));
        } catch (Exception ex) {
            return Mono.error(ex);
        }
    }

	public Flux<Marchent> getAllMarchentByIdUsingWebFlux() {
		 try {
	            CompletableFuture<List<Marchent>> future =
	                    CompletableFuture.supplyAsync(() -> dynamoDBMapper.scan(Marchent.class, new DynamoDBScanExpression()));

	            return Mono.fromCompletionStage(future)
	                    .flatMapMany(item -> Flux.fromStream(item.stream()))
	                    .onErrorMap(throwable -> new DynamoDbException(DYNAMO_ERROR, throwable));
	        } catch (Exception ex) {
	            return Flux.error(ex);
	        }
	}
}
