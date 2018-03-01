package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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

    @ElementCollection
    @Column(name = "endpointUrl")
    private Collection<String> endpoints = new ArrayList<>();

    public void addEndpoint(String endpoint) {
        this.endpoints.add(endpoint);
    }
}
