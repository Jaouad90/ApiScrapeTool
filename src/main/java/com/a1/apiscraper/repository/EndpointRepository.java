package com.a1.apiscraper.repository;

import com.a1.apiscraper.domain.Endpoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointRepository extends CrudRepository<Endpoint, Long> {
}
