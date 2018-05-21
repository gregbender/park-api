package com.gregbender.parking.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gregbender.parking.model.ParkingAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ParkingService {

    @Autowired
    DynamoService dynamoService;

    @Autowired
    S3Service s3Service;

    @Autowired
    private ParkingService parkingService;

    public void handleUpload(String origFileName, byte[] fileAsBytes) {

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream((fileAsBytes)));

            ParkingAttempt parkingAttempt = new ParkingAttempt();
            parkingAttempt.setHeight(image.getHeight());
            parkingAttempt.setWidth(image.getWidth());
            parkingAttempt.setOrigFileName(origFileName);
            parkingAttempt.setImageData(fileAsBytes);
            parkingService.addParkingAttempt(parkingAttempt);
        } catch (AmazonServiceException e1) {
            System.err.println(e1.getErrorMessage());
        }
        catch (Exception e2) {
            System.err.println(e2.getMessage());
        }
    }

    public void addParkingAttempt(ParkingAttempt parkingAttempt) {
        try {
            dynamoService.save(parkingAttempt);
            ObjectMetadata md = new ObjectMetadata();

            md.setContentLength(parkingAttempt.getImageData().length);

            s3Service.add(parkingAttempt.getId(),  new ByteArrayInputStream(parkingAttempt.getImageData()), md);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParkingAttempt vote(String id, ParkingAttempt.VOTE_DIRECTION voteDirection) {

        ParkingAttempt parkingAttempt = new ParkingAttempt();
        parkingAttempt.setId(id);

        parkingAttempt = dynamoService.get(parkingAttempt);
        parkingAttempt.vote(voteDirection);
        dynamoService.save(parkingAttempt);
        return parkingAttempt;
    }


    public List<ParkingAttempt> getAllParkingAttempts() {
        List<ParkingAttempt> itemList = dynamoService.getAll();
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(itemList.get(i).getId());
        }
        return itemList;
    }

}