package com.perso.bio.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perso.bio.constants.MessageConstants;
import com.perso.bio.model.House;
import com.perso.bio.service.house.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping(path = "/house")
public class HouseController {

    private final HouseService houseService;
    private final ObjectMapper objectMapper;
    //private static final org.slf4j.Logger log = LoggerFactory.getLogger(HouseController.class);
    @Autowired
    public HouseController(HouseService houseService, ObjectMapper objectMapper) {
        this.houseService = houseService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createHouse(@RequestPart("File") MultipartFile file, @RequestPart("house") String house) throws FileNotFoundException, JsonProcessingException {
        House houseObj;
        houseObj = objectMapper.readValue(house, House.class);
        this.houseService.createHouse(houseObj, file);
        return new ResponseEntity<>(MessageConstants.CREATE, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<House> getHouse(@PathVariable Integer id) throws MalformedURLException {
        House house = this.houseService.getHouse(id);
        return ResponseEntity.ok(house);
    }


    @GetMapping(path = "/all")
    public ResponseEntity<List<House>> getAllHouse() {
        List<House> houses = this.houseService.getAllHouses();
        return ResponseEntity.ok(houses);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateHouse(@PathVariable Integer id, @RequestPart("File") MultipartFile file, @RequestPart("house") String house) throws FileNotFoundException, JsonProcessingException {
        House houseObj;
        houseObj = objectMapper.readValue(house, House.class);
        this.houseService.updateHouse(id, houseObj, file);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteHouse(@PathVariable Integer id) throws FileNotFoundException {
        this.houseService.deleteHouse(id);
        return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.OK);
    }


}
