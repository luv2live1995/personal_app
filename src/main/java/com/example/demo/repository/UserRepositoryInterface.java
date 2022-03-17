package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

public interface UserRepositoryInterface extends CrudRepository<User, Integer> {
	
}
