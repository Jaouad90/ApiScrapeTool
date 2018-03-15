package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Endpoint {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "FF iets invullen")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Map<Long, Result> results = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Map<Long, HyperMedia> foundHypermedia = new HashMap<>();

    public void addFoundHyperMedia(HyperMedia hyperMedia) {
        foundHypermedia.put(hyperMedia.getId(), hyperMedia);
    }
}
