package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "apiConfig")
public class APIConfig {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name ="api_FK")
    private API api;

    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    private List<Decorator> decorators = new ArrayList<>();

    public void addDecorator(Decorator decorator){
        this.decorators.add(decorator);
    }

}