package com.group15.jacksonpollock.repo;

import com.group15.jacksonpollock.entity.MpxnData;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MpxnDataRepo extends MongoRepository<MpxnData, String> {
    List<MpxnData> findByPostcodeStartsWith(String postcode);

    Integer countByPostcode(String postcode);

    @Aggregation(pipeline = {
            "{$match: {'devices.deviceStatus': 'NOT COMMISSIONED'}}"
    })
    List<MpxnData> findNotCommissionedDevices();

    @Aggregation(pipeline = {
            "{$match: {'devices.deviceStatus': 'NOT COMMISSIONED'}}",
            "{$match: {'postcode': {$regex: ?0}}}"
    })
    List<MpxnData> findNotCommissionedDevicesByPostcodeStartsWith(String postcode);
}


