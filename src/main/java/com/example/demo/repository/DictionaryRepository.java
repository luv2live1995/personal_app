package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Dictionary;

public interface DictionaryRepository extends CrudRepository<Dictionary, Integer> {
	
	
	
}
