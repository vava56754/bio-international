package com.perso.bio.service.house;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.controller.StorageController;
import com.perso.bio.model.House;

import com.perso.bio.repository.HouseRepository;
import com.perso.bio.service.storage.FilesStorageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.FileNotFoundException;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class HouseServiceImpl implements HouseService {

    HouseRepository houseRepository;

    FilesStorageService filesStorageService;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository, FilesStorageService filesStorageService) {
        this.houseRepository = houseRepository;
        this.filesStorageService = filesStorageService;
    }

    @Override
    public void createHouse(House house, MultipartFile file) throws FileNotFoundException {
        saveFile(house, file);
        this.houseRepository.save(house);
    }

    @Override
    public House getHouse(Integer houseId) throws MalformedURLException {
        House house = this.houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException(MessageConstants.HOUSE_SERVICE_ERROR_MESSAGE + houseId));
        String url = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder
                        .on(StorageController.class)
                        .getFile(house.getHouseLink()))
                .build()
                .toUriString();
        house.setHouseLink(url);

        return house;
    }

    @Override
    public void updateHouse(Integer houseId, House house, MultipartFile file) throws FileNotFoundException {
        Optional<House> existingHouse = this.houseRepository.findById(houseId);
        if (existingHouse.isPresent()) {
            House houseUpdate = existingHouse.get();
            houseUpdate.setHouseName(Optional.ofNullable(house.getHouseName()).orElse(houseUpdate.getHouseName()));
            houseUpdate.setHouseDescription(Optional.ofNullable(house.getHouseDescription()).orElse(houseUpdate.getHouseDescription()));
            //houseUpdate.setHouseLink(Optional.ofNullable(house.getHouseLink()).orElse(houseUpdate.getHouseLink()));
            if (!file.isEmpty()) {
                deleteFileForHouse(houseId);
                saveFile(houseUpdate, file);
            }
            this.houseRepository.save(houseUpdate);
        } else {
            throw new EntityNotFoundException(MessageConstants.HOUSE_SERVICE_ERROR_MESSAGE + houseId);
        }
    }

    @Override
    public List<House> getAllHouse() {
        List<House> houses = this.houseRepository.findAll();
        houses.forEach(house -> {
            String url = null;
            try {
                url = MvcUriComponentsBuilder
                        .fromMethodCall(MvcUriComponentsBuilder
                                .on(StorageController.class)
                                .getFile(house.getHouseLink()))
                        .build()
                        .toUriString();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            house.setHouseLink(url);
        });
        return houses;
    }

    @Override
    public void deleteHouse(Integer houseId) throws FileNotFoundException {
        Optional<House> existingHouse = this.houseRepository.findById(houseId);
        if (existingHouse.isPresent()) {
            deleteFileForHouse(houseId);
            this.houseRepository.deleteById(houseId);
        } else {
            throw new EntityNotFoundException(MessageConstants.HOUSE_SERVICE_ERROR_MESSAGE + houseId);
        }

    }

    private void deleteFileForHouse(Integer houseId) throws FileNotFoundException {
        House house = this.houseRepository.findById(houseId).orElseThrow();
        try {
            this.filesStorageService.deleteByName(house.getHouseLink());
        } catch (Exception e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    private void saveFile(House house, MultipartFile file) throws FileNotFoundException {
        try {
            String filename = this.filesStorageService.save(file);
            house.setHouseLink(filename);
        } catch (Exception e) {
            throw new FileNotFoundException(e.getMessage());
        }

    }
}
