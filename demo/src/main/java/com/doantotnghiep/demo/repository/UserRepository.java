package com.doantotnghiep.demo.repository;

import com.doantotnghiep.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    User findUserByusername(String username);

    User findUserByphone(String phone);

    Long countUserByEnabled(Boolean enabled);
}
