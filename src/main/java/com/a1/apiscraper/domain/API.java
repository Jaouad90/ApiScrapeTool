package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

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

    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    private String username;

    private String password;

    @NotEmpty(message = "Base URL cannot be not empty!")
    @URL(message = "Please insert a valid URL!")
    private String baseUrl;


    @OneToMany(mappedBy="api", targetEntity = Endpoint.class)
    private Collection<Endpoint> endpoints = new ArrayList<>();


    public void addEndpoint(Endpoint endpoint) {
        this.endpoints.add(endpoint);
    }
}
