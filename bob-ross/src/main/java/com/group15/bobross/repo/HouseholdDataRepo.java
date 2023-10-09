package com.group15.bobross.repo;

import com.group15.bobross.entity.MpxnData;
import com.group15.bobross.entity.NumberOfHouseholds;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseholdDataRepo extends MongoRepository<NumberOfHouseholds, String> {

}
