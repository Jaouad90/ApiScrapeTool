package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Result {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String result;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTimeStamp;

    @OneToMany(cascade = CascadeType.ALL)
    private Map<Long, HyperMedia> foundHypermedia = new HashMap<>();

    public void addFoundHyperMedia(HyperMedia hyperMedia) {
        foundHypermedia.put(hyperMedia.getId(), hyperMedia);
    }

}
