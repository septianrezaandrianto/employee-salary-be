package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.Employee.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
