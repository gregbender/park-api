package com.gregbender.parking.controller;

import com.gregbender.parking.model.ParkingAttempt;
import com.gregbender.parking.service.ParkingService;
import com.gregbender.parking.service.S3Service;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Basic Spring web service controller that handles all GET requests.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private S3Service s3Service;

    private static final String MESSAGE_FORMAT = "Niner22222Hello %s!";

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", path = "/parkingAttempts/all")
    public ResponseEntity<List<ParkingAttempt>> listAllParkingAttempts() {
        return ResponseEntity.ok(parkingService.getAllParkingAttempts());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/view/{id}")
    public ResponseEntity<String> view(@PathVariable("id") String id, RedirectAttributes redirectAttributes, HttpServletResponse resp) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", s3Service.getUrl(id));
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", path = "/vote/{id}")
    public ResponseEntity<ParkingAttempt> vote(@PathVariable("id") String id, @RequestParam(name = "type", required = true) String type) {

        ParkingAttempt.VOTE_DIRECTION voteDirection = ParkingAttempt.VOTE_DIRECTION.valueOf(type);
        ParkingAttempt parkingAttempt = parkingService.vote(id, voteDirection);
        return ResponseEntity.ok(parkingAttempt);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    public ResponseEntity<String> handleUpload(InputStream dataStream) {
        /// uploadService.saveCase(myCase);

        try {
            byte[] bytes = IOUtils.toByteArray(dataStream);
            byte[] newbytes = Base64.decodeBase64(bytes);

            parkingService.handleUpload("ingobankgo.jpg", newbytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("");
    }
}