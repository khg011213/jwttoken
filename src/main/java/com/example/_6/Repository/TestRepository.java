package com.example._6.Repository;

import com.example._6.Entity.TestEntitly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<TestEntitly,Integer> {
    List<TestEntitly> findAll();
}
