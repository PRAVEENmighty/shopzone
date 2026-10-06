package com.example.shopzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopzone.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}