package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
public class APIMemento {
    @Id
    @GeneratedValue
    private Long Id;

    private String state;

    private String baseUrl;

    @OneToMany(cascade = CascadeType.ALL)
    private Map<Long, Endpoint> endpoints = new HashMap<>();


    public APIMemento(String state, Map<Long, Endpoint> endpoints, String baseUrl) {
        this.state = state;
        this.endpoints = endpoints;
        this.baseUrl = baseUrl;
    }

}
