package com.gregbender.parking.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@DynamoDBTable(tableName="parking")
@Getter
@Setter
@Accessors(chain = true)
public class ParkingAttempt {
    public enum VOTE_DIRECTION {up, down}


    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName="height")
    private int height;

    @DynamoDBAttribute(attributeName="width")
    private int width;

    @DynamoDBAttribute(attributeName="origFileName")
    private String origFileName;

    @DynamoDBAttribute(attributeName="upVotes")
    private int upVotes;

    @DynamoDBAttribute(attributeName="downVotes")
    private int downVotes;

    private byte[] imageData;

    public void vote(VOTE_DIRECTION vote_direction) {
        if (vote_direction.equals(VOTE_DIRECTION.up)) {
            upVotes++;
        }
        else {
            downVotes++;
        }
    }
}