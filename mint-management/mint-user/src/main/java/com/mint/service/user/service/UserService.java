package com.mint.service.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mint.service.user.dao.UserDao;
import com.mint.service.user.entity.UserEntity;

@Service
public class UserService {
		
	@Autowired
	private UserDao userDao;
	
	
	public void saveOrUpdateUser(UserEntity entity) {
		userDao.save(entity);
	}
	
	public UserEntity findUserById(Long id) {
		return userDao.findById(id).get();
	}
	
	public List<UserEntity> findUserByIds(List<Long> ids) {
		return userDao.findAllById(ids);
	}
	
}
