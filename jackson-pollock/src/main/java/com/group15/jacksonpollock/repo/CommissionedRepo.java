package com.group15.jacksonpollock.repo;

import com.group15.jacksonpollock.entity.CommissionedDistribution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionedRepo extends MongoRepository<CommissionedDistribution, String> {
}


