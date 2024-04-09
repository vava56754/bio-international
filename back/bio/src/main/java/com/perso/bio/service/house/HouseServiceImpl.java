package com.perso.bio.service.house;


import com.perso.bio.constants.MessageConstants;
import com.perso.bio.controller.StorageController;
import com.perso.bio.model.House;

import com.perso.bio.model.Product;
import com.perso.bio.repository.HouseRepository;
import com.perso.bio.repository.ProductRepository;
import com.perso.bio.service.storage.FilesStorageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.FileNotFoundException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class HouseServiceImpl implements HouseService {

    HouseRepository houseRepository;

    ProductRepository productRepository;

    FilesStorageService filesStorageService;

    @Autowired
    public HouseServiceImpl(HouseRepository houseRepository, ProductRepository productRepository, FilesStorageService filesStorageService) {
        this.houseRepository = houseRepository;
        this.productRepository = productRepository;
        this.filesStorageService = filesStorageService;
    }

    @Override
    public void createHouse(House house, MultipartFile file) throws FileNotFoundException {
        this.saveFile(house, file);
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
            if (!"blob".equals(file.getOriginalFilename())) {
                this.deleteFileForHouse(houseId);
                this.saveFile(houseUpdate, file);
            }
            this.houseRepository.save(houseUpdate);
        } else {
            throw new EntityNotFoundException(MessageConstants.HOUSE_SERVICE_ERROR_MESSAGE + houseId);
        }
    }

    @Override
    public List<House> getAllHouses() {
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
            List<Product> products;
            products = this.productRepository.findByHouseHouseId(houseId);
            if (!products.isEmpty()) {
                for (Product product : products) {
                    product.setHouse(null);
                }
            }

            this.deleteFileForHouse(houseId);
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
