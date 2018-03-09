package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "api")
public class API {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    @NotEmpty(message = "Base URL cannot be not empty!")
    @URL(message = "Please insert a valid URL!")
    private String baseUrl;

    private String state;

    @OneToOne
    private APIConfig config;


    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Map<Long, Endpoint> endpoints = new HashMap<>();


    public void addEndpoint(Endpoint endpoint) {
        assert(endpoint.getId() != null);
        endpoints.put(endpoint.getId(), endpoint );
    }

    public APIMemento saveStateToMemente() {
        return new APIMemento(name, state, endpoints, baseUrl);
    }

    public void getStateFromMemento(APIMemento memento) {
        name = memento.getName();
        state = memento.getState();
        endpoints = memento.getEndpoints();
        baseUrl = memento.getBaseUrl();
    }

}
