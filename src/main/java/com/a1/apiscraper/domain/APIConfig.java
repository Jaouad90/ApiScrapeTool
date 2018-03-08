package com.a1.apiscraper.domain;

import com.a1.apiscraper.logic.APIScraperDecorator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

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

    @OneToMany(cascade = CascadeType.MERGE)
    private Map<Long, APIScraperDecorator> decorators = new HashMap<>();


    public void addDecorator(APIScraperDecorator decorator) {


    }

}
