package com.perso.bio.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    void init() throws FileSystemException;

    String save(MultipartFile file) throws FileSystemException;

    Resource load(String filename) throws MalformedURLException;

    boolean findByName(String filename);

    Stream<Path> loadAll();

    void deleteAll();

    void deleteByName(String filename) throws IOException;


}
