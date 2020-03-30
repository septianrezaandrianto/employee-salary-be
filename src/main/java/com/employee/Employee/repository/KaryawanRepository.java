package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.Employee.model.Karyawan;

public interface KaryawanRepository extends JpaRepository<Karyawan, Integer>{

}
