package com.perso.bio.service.storage;


import com.perso.bio.constants.MessageConstants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get(MessageConstants.FILE_PATH);

    @Override
    public void init() throws FileSystemException {
        try {
            Files.createDirectories(root);
        } catch (Exception e) {
            throw new FileSystemException(MessageConstants.FILE_ERROR_UPLOAD + e.getMessage());
        }
    }

    @Override
    public String save(MultipartFile file) throws FileSystemException {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            return fileName;
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new FileAlreadyExistsException(MessageConstants.FILE_ALREADY_EXIST + e.getMessage());
            }

            throw new FileSystemException(e.getMessage());
        }
    }


    @Override
    public Resource load(String filename) throws MalformedURLException {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(MessageConstants.FILE_READ_ERROR);
            }

        } catch (MalformedURLException e) {
            throw new MalformedURLException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void deleteByName(String filename) throws IOException {
        Files.delete(root.resolve(filename));
    }

    @Override
    public boolean findByName(String filename) {
        return Files.exists(root.resolve(filename));
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
