package com.perso.bio.controller;

import com.perso.bio.service.storage.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.MalformedURLException;
import java.nio.file.Files;


@RestController
@RequestMapping(path = "/files")
public class StorageController {


    private final FilesStorageService filesStorageService;

    @Autowired
    public StorageController(FilesStorageService filesStorageService) {
        this.filesStorageService = filesStorageService;
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        Resource file = filesStorageService.load(filename);
        MediaType mediaType = MediaType.parseMediaType("image/jpeg");

        try {
            mediaType = MediaType.parseMediaType(Files.probeContentType(file.getFile().toPath()));
        } catch (Exception e) {
            throw new MalformedURLException(e.getMessage());
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(mediaType)
                .body(file);
    }
}
