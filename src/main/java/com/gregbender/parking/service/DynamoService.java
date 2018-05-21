package com.gregbender.parking.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.gregbender.parking.model.ParkingAttempt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamoService {



    public DynamoDBMapper getDynamoDBMapper() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        return mapper;
    }

    public void save(ParkingAttempt parkingAttempt) {
        this.getDynamoDBMapper().save(parkingAttempt);
    }

    public ParkingAttempt get(ParkingAttempt parkingAttempt) {
        DynamoDBQueryExpression<ParkingAttempt> queryExpression = new DynamoDBQueryExpression<ParkingAttempt>();
        queryExpression.setHashKeyValues(parkingAttempt);
        List<ParkingAttempt> itemList = this.getDynamoDBMapper().query(ParkingAttempt.class, queryExpression);
        return itemList.get(0);
    }

    public List<ParkingAttempt> getAll() {
//
//        DynamoDB
//
//        ParkingAttempt parkingAttempt = new ParkingAttempt();
//        parkingAttempt.s
//
//
//
//        DynamoDBQueryExpression<ParkingAttempt> queryExpression = new DynamoDBQueryExpression<ParkingAttempt>();
//        queryExpression.set
//        queryExpression.setHashKeyValues(parkingAttempt);
        List<ParkingAttempt> itemList = this.getDynamoDBMapper().scan(ParkingAttempt.class, new DynamoDBScanExpression());

        return itemList;

    }

}