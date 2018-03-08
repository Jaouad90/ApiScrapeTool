package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class CareTaker {
    @GeneratedValue
    @Id
    private Long Id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<APIMemento> mementoList = new ArrayList<>();

    public void add(APIMemento state){
        mementoList.add(state);
    }

    public APIMemento get(int index) {
        return mementoList.get(index);
    }
}
