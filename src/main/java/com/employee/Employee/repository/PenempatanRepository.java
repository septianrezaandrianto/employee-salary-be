package com.employee.Employee.repository;

import com.employee.Employee.model.Penempatan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenempatanRepository extends JpaRepository<Penempatan, Integer> {

}