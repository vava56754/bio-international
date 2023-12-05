package com.perso.bio.service.house;

import com.perso.bio.model.House;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

public interface HouseService {

    void createHouse(House house, MultipartFile file) throws FileNotFoundException;

    House getHouse(Integer houseId) throws MalformedURLException;

    List<House> getAllHouse();

    void updateHouse( Integer houseId, House house, MultipartFile file) throws FileNotFoundException;

    void deleteHouse(Integer houseId) throws FileNotFoundException;


}
