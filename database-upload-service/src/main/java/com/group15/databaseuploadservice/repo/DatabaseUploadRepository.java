package com.group15.databaseuploadservice.repo;

import com.group15.databaseuploadservice.entity.DataEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseUploadRepository extends MongoRepository<DataEntry, String> {
}

