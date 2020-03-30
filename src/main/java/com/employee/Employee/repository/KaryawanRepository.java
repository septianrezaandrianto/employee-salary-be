package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Employee.model.Karyawan;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan, Integer>{

}
