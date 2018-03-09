package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

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
    private Long Id;

    private String name;

    private String state;

    private String baseUrl;

    @Transient
    private Map<Long, Endpoint> endpoints = new HashMap<>();


    public APIMemento(String name, String state, Map<Long, Endpoint> endpoints, String baseUrl) {
        this.name = name;
        this.state = state;
        this.endpoints = endpoints;
        this.baseUrl = baseUrl;
    }

}
