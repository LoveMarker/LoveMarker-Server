package com.lovemarker.domain.user.repository;

import com.lovemarker.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
