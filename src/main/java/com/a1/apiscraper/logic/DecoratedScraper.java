package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;


public abstract class DecoratedScraper extends APIScraper {

    public DecoratedScraper(API api) {
        super(api);
    }

}
