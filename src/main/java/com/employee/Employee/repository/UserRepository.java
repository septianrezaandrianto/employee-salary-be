package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.Employee.model.User;
import com.employee.Employee.model.UserId;

public interface UserRepository extends JpaRepository<User, UserId> {

}
