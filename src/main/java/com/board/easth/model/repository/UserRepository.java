package com.board.easth.model.repository;

import com.board.easth.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

}