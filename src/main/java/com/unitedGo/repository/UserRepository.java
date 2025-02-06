package com.unitedGo.repository;

import com.unitedGo.entity.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {

    Optional<Users> findByUserName(String userName);
}
