package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class CareTaker {

    @GeneratedValue
    @Id
    private Long Id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private API api;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<APIMemento> mementoList;

    public void add(APIMemento state){
        mementoList.add(state);
    }

    public APIMemento get(int index) {
        return mementoList.get(index);
    }
}
