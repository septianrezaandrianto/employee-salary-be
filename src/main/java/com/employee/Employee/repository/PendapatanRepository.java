package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Employee.model.Pendapatan;

@Repository
public interface PendapatanRepository extends JpaRepository<Pendapatan, Integer>{

}
