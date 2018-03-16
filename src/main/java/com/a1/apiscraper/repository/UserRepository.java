package com.a1.apiscraper.repository;

import com.a1.apiscraper.domain.User;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String userame);
}
