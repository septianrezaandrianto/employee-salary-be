package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Employee.model.Tingkatan;

@Repository
public interface TingkatanRepository extends JpaRepository<Tingkatan, Integer> {

}
