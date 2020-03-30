package com.employee.Employee.repository;

import com.employee.Employee.model.KategoriKemampuan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategoriKemampuanRepository extends JpaRepository<KategoriKemampuan, Integer> {

}