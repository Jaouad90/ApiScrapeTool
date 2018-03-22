package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class APIMemento {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String state;

    private String baseUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    private Map<Long, Endpoint> endpoints = new HashMap<>();


    public APIMemento(String name, String state, Map<Long, Endpoint> endpoints, String baseUrl) {
        this.name = name;
        this.state = state;
        this.endpoints = endpoints;
        this.baseUrl = baseUrl;
    }

}
