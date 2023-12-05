package com.perso.bio.controller;


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

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createHouse(@RequestPart("file") MultipartFile file, @RequestPart("house") House house) throws FileNotFoundException {
        this.houseService.createHouse(house, file);
        return new ResponseEntity<>(MessageConstants.CREATE, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<House> getHouse(@PathVariable Integer id) throws MalformedURLException {
        House house = this.houseService.getHouse(id);
        return ResponseEntity.ok(house);
    }


    @GetMapping(path = "/all")
    public ResponseEntity<List<House>> getAllHouse() {
        List<House> houses = this.houseService.getAllHouse();
        return ResponseEntity.ok(houses);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<String> updateHouse(@PathVariable Integer id, @RequestPart("file") MultipartFile file, @RequestPart("house") House house) throws FileNotFoundException {
        this.houseService.updateHouse(id, house, file);
        return new ResponseEntity<>(MessageConstants.UPDATE, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteHouse(@PathVariable Integer id) throws FileNotFoundException {
        this.houseService.deleteHouse(id);
        return new ResponseEntity<>(MessageConstants.DELETE, HttpStatus.NO_CONTENT);
    }


}
