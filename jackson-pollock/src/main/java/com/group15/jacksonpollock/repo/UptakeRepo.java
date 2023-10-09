package com.group15.jacksonpollock.repo;

import com.group15.jacksonpollock.entity.UptakeDistribution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UptakeRepo extends MongoRepository<UptakeDistribution, String> {
}
