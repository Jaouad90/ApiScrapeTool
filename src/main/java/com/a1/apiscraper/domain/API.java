package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalTime;
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

    @ManyToOne(cascade = CascadeType.ALL)
    public TimeInterval timeInterval;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private APIConfig config;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CareTaker careTaker = new CareTaker();

    @Valid
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Map<Long, Endpoint> endpoints = new HashMap<>();

    public void addEndpoint(Endpoint endpoint) {
        assert(endpoint.getId() != null);
        endpoints.put(endpoint.getId(), endpoint);
    }

    public APIMemento saveStateToMemente() {
        Map<Long, Endpoint> endpointMap = new HashMap<>();
        endpointMap = endpoints;
        return new APIMemento(name, state, endpointMap, baseUrl);
    }

    public void getStateFromMemento(APIMemento memento) {
        name = memento.getName();
        state = memento.getState();
        endpoints = new HashMap<>(memento.getEndpoints());
        baseUrl = memento.getBaseUrl();
    }




}
