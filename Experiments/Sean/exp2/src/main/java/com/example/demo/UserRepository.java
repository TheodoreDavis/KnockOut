package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.User;

/**
 * 
 * @author Sean Griffen
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
}
