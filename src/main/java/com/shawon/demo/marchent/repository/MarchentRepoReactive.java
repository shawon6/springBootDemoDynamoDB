package com.shawon.demo.marchent.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.shawon.demo.marchent.entity.Marchent;

@Repository
public interface MarchentRepoReactive extends ReactiveCrudRepository<Marchent, String>{
	
}
