package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Employee.model.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Integer>{

}
