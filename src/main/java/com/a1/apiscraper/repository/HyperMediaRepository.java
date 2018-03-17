package com.a1.apiscraper.repository;

import com.a1.apiscraper.domain.HyperMedia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HyperMediaRepository extends CrudRepository<HyperMedia, Long> {
}
