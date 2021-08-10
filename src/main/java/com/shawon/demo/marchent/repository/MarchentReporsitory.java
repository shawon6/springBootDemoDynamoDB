package com.shawon.demo.marchent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shawon.demo.marchent.entity.Marchent;

@Repository
public interface MarchentReporsitory extends CrudRepository<Marchent, Integer>{

	@Query(value = "SELECT * FROM samplemarchent", nativeQuery = true)
	List<Marchent> getAllMarchent();
	
}
