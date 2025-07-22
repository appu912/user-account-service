package com.progmatic.store.account.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.progmatic.store.account.entity.User;

@Repository(value = "userRepository")
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmailId(String emailId);
}
