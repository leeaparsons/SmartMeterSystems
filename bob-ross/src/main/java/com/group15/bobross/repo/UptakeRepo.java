package com.group15.bobross.repo;

import com.group15.bobross.entity.UptakeDistribution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UptakeRepo extends MongoRepository<UptakeDistribution, String> {
}