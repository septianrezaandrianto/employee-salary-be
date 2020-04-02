package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.employee.Employee.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Modifying
	@Query
	(value = "UPDATE 'user' SET username = :username, password = :password, status = :status WHERE id_user = :id", nativeQuery = true)
	@Transactional
	void updateEmployee(@Param("id") Integer id, @Param("username") String username,
			   @Param("password") String password, @Param("status") int status);
	
}
