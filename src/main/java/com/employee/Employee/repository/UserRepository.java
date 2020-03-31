package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Employee.model.User;
import com.employee.Employee.model.UserId;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
