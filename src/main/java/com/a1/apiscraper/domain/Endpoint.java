package com.a1.apiscraper.domain;

import com.sun.corba.se.spi.activation.EndPointInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Endpoint {

    @Id
    @GeneratedValue
    private Long id;
    
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Map<Long, Result> results = new HashMap<>();
}
