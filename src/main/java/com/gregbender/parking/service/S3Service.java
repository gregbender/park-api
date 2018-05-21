package com.gregbender.parking.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    private static final String BUCKET_NAME = "com.gregbender.parkings3";

    public AmazonS3 getS3Service() {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
        return s3;
    }

    public void add(String id, InputStream is, ObjectMetadata md) throws IOException {
        this.getS3Service().putObject(BUCKET_NAME, id, is, md);
    }

    public String getUrl(String id) {
        S3Object s3Object = this.getS3Service().getObject(BUCKET_NAME, id);
        return s3Object.getRedirectLocation();
    }
}