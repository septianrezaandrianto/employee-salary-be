package com.employee.Employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.employee.Employee.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Modifying
	@Query
	(value = "UPDATE 'User' SET usename = :username, password = :password, status = :status WHERE id_user = :id AND username = username", nativeQuery = true)
	@Transactional
	void updateUser(@Param("id") Long id, @Param("username") String username, 
						@Param("password") String password,  @Param("status") short status);
	
	@Modifying
	@Query
	(value = "DELETE FROM 'User' Where id_user = :id", nativeQuery = true)
	@Transactional
	void deteleUser(@Param("id") Long id);
}
