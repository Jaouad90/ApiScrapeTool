package com.a1.apiscraper.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@Entity
@NoArgsConstructor
public class CareTaker {

    @GeneratedValue
    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<APIMemento> mementos = new ArrayList<>();

    public void add(APIMemento state){
        mementos.add(state);
    }

    public APIMemento get(int index) {
        return mementos.get(index);
    }
}
