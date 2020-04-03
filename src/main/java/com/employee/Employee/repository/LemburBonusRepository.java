package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.Employee.model.LemburBonus;

@Repository
public interface LemburBonusRepository extends JpaRepository<LemburBonus, Long> {

}
