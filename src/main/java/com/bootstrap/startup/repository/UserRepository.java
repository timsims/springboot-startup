package com.bootstrap.startup.repository;

import com.bootstrap.startup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author timsims
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 通过 email 查找用户
     *
     * @param email 用户邮箱
     * @return User
     */
    User findByEmail(String email);
}
