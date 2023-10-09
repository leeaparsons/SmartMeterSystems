package com.group15.bobross.repo;

import com.group15.bobross.entity.MpxnData;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MpxnDataRepo extends MongoRepository<MpxnData, String> {
    List<MpxnData> findByPostcodeStartsWith(String postcode);

//    @Aggregation(pipeline = {
//            "{$match: {postcode: {$regex: '^?0'}}}"
//    })
    int countByPostcodeStartingWith(String postcode);

    @Aggregation(pipeline = {
            "{$match: {'devices.deviceStatus': 'NOT COMMISSIONED'}}",
            "{$match: {'postcode': {$regex: ?0}}}"
    })
    List<MpxnData> findNotCommissionedDevicesByPostcodeStartsWith(String postcode);
}
