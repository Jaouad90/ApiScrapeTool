package com.a1.apiscraper.repository;

import com.a1.apiscraper.logic.APIScraperDecorator;
import org.springframework.data.repository.CrudRepository;

public interface DecoratorRepository extends CrudRepository<APIScraperDecorator, Long> {
}
