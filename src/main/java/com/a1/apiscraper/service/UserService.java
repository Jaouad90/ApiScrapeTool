package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
