package com.shawon.demo.marchent.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.shawon.demo.marchent.entity.Marchent;

public interface MarchentRepoReactive extends ReactiveCrudRepository<Marchent, String>{
	
}
