package com.gregbender.parking.controller;

import com.gregbender.parking.model.ParkingAttempt;
import com.gregbender.parking.service.ParkingService;
import com.gregbender.parking.service.S3Service;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Basic Spring web service controller that handles all GET requests.
 */
@RestController
@RequestMapping("/test")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private S3Service s3Service;

    private static final String MESSAGE_FORMAT = "Niner22222Hello %s!";

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity helloWorldGet(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.ok(createResponse(name));
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity helloWorldPost(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.ok(createResponse(name));
    }

    private String createResponse(String name) {
        return new JSONObject().put("Output", String.format(MESSAGE_FORMAT, name)).toString();
    }

















//
//
// //   @PostMapping("/upload")
//    public ResponseEntity<String> handleUpload(@RequestParam("file") MultipartFile file) {
//        /// uploadService.saveCase(myCase);
//
//        try {
//            InputStream inputStream = file.getInputStream();
//            byte[] fileAsBytes = IOUtils.toByteArray(inputStream);
//            parkingService.handleUpload(file.getOriginalFilename(), fileAsBytes);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.ok("");
//    }
//
//  //  @GetMapping("/parkingAttempts/all")
//    public ResponseEntity<List<ParkingAttempt>> listAllParkingAttempts() {
//        return ResponseEntity.ok(parkingService.getAllParkingAttempts());
//    }
//
//   // @GetMapping("/vote/{id}")
//    public ResponseEntity<ParkingAttempt> vote(@PathVariable("id") String id, @RequestParam(name = "type", required = true) String type) {
//
//        ParkingAttempt.VOTE_DIRECTION voteDirection = ParkingAttempt.VOTE_DIRECTION.valueOf(type);
//        ParkingAttempt parkingAttempt = parkingService.vote(id, voteDirection);
//        return ResponseEntity.ok(parkingAttempt);
//    }
//
//   // @GetMapping("/view/{id}")
//    public ResponseEntity<String> view(@PathVariable("id") String id, RedirectAttributes redirectAttributes, HttpServletResponse resp) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", s3Service.getUrl(id));
//        return new ResponseEntity<String>(headers,HttpStatus.FOUND);
//    }
//
//




}