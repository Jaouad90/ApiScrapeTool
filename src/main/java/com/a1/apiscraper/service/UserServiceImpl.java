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
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RepositoryServiceInterface repositoryService;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>((Collection<? extends Role>) repositoryService.getAllRoles()));
        repositoryService.saveUser(user);
    }
}
