package com.mint.service.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mint.service.user.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

}
