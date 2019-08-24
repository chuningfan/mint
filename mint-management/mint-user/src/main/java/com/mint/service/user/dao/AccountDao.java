package com.mint.service.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mint.service.user.entity.AccountEntity;

@Repository
public interface AccountDao extends JpaRepository<AccountEntity, Long> {
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE accounts SET password = ?1 WHERE username = ?2 AND password = ?3")
	public void updatePassword(String newPassword, String username, String oldPassword);
	
	@Query("SELECT a FROM AccountEntity a WHERE a.username = ?1 AND a.password = ?2")
	public AccountEntity findByCredential(String username, String password);
	
}
