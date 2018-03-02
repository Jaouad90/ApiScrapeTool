package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.Role;
import com.a1.apiscraper.repository.RoleRepository;
import com.a1.apiscraper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.a1.apiscraper.domain.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<Role>((Collection<? extends Role>) roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
