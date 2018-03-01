package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
public class API {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String password;

    private String baseUrl;

    @OneToMany(cascade = CascadeType.ALL)
    private ArrayList<String> endpoints = new ArrayList<>();

    public void addEndpoint(String endpoint) {
        this.endpoints.add(endpoint);
    }
}
