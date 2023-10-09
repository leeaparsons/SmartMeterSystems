package com.group15.interactivemapservice.repo;

import com.group15.interactivemapservice.model.TableData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;

public interface TableDataRepository extends MongoRepository<TableData, String> {

    Page<TableData> findAll(Pageable pageable);

    @Query("{ $or: [{'mpxn':  { $regex: ?0, $options: 'i'}}, {'postcode' : { $regex: ?0, $options: 'i'}}]}")
    Page<TableData> findByMpxnOrPostcodeContainingIgnoreCase(String search, Pageable pageable);
}
