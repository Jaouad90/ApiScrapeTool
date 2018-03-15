package com.a1.apiscraper.repository;

import com.a1.apiscraper.domain.ScrapeBehavior;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapeBehaviorRepository extends CrudRepository<ScrapeBehavior, Long>{
}
