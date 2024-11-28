package com.example._6.Repository;

import com.example._6.Entity.TestEntitly;
import com.example._6.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
