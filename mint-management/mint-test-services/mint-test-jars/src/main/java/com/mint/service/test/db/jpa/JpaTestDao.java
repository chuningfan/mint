package com.mint.service.test.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mint.service.test.entity.TestEntity;

@Repository
public interface JpaTestDao extends JpaRepository<TestEntity, Long> {

}
