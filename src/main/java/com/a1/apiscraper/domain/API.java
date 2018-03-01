package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "api")
public class API {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String password;

    private String baseUrl;


    @OneToMany(mappedBy="api", targetEntity = Endpoint.class)
    private Collection<Endpoint> endpoints = new ArrayList<>();


    public void addEndpoint(Endpoint endpoint) {
        this.endpoints.add(endpoint);
    }
}
