package com.a1.apiscraper.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
