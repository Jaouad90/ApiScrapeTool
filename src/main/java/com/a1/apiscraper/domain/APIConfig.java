package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "apiConfig")
public class APIConfig {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Decorator> decorators = new ArrayList<>();

    @OneToOne
    private ScrapeBehavior scrapeBehavior;

    public void addDecorator(Decorator decorator){
        this.decorators.add(decorator);
    }
}
