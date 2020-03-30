package com.employee.Employee.repository;

import com.employee.Employee.model.Kemampuan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KemampuanRepository extends JpaRepository<Kemampuan, Integer> {

}