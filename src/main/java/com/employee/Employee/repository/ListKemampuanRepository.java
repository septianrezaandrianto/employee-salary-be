package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Employee.model.ListKemampuan;

@Repository
public interface ListKemampuanRepository extends JpaRepository<ListKemampuan, Integer>{

}
