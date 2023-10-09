package com.group15.meterdataservice.repo;

import com.group15.meterdataservice.entity.MeterData;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterDataRepository extends MongoRepository<MeterData, String> {
    @Aggregation(pipeline = {
            "{$match: {postcode: {$regex: '^?0'}}}"
    })
    List<MeterData> findByPostcodeStartingWith(String postcodeArea);
    List<MeterData> findByMpxn(String mpxn);


    @Aggregation(pipeline = {
            "{$match: {'devices.deviceStatus': 'NOT COMMISSIONED'}}",
            "{$addFields: {devices: {$filter: {input: '$devices', as: 'device', cond: {$eq: ['$$device.deviceStatus', 'NOT COMMISSIONED']}}}}}"
    })
    List<MeterData> findUncommissionedDevices();

    @Aggregation(pipeline = {
            "{$match: {'devices.deviceStatus': 'NOT COMMISSIONED'}}",
            "{$addFields: {devices: {$filter: {input: '$devices', as: 'device', cond: {$eq: ['$$device.deviceStatus', 'NOT COMMISSIONED']}}}}}",
            "{$match: {'postcode': {$regex: ?0}}}"
    })
    List<MeterData> findNotCommissionedDevicesByPostcodeStartsWith(String postcodeArea);
}

