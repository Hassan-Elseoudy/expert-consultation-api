package com.code4ro.legalconsultation.storage.service.impl;

import com.code4ro.legalconsultation.storage.service.StorageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Profile("dev")
public class FilesystemStorageService implements StorageApi {

    @Value("${storage.filesystem.directory}")
    private String customStoreDirPath;

    private File storeDir;

    @PostConstruct
    private void prepareStoreDir() {
        final String home = System.getProperty("user.home");
        storeDir = new File(home, customStoreDirPath);
        if (!storeDir.exists()) {
            storeDir.mkdir();
        }
    }

    @Override
    public String storeFile(final MultipartFile document) throws IOException {
        // add a random string to each file in roder to avoid duplicates
        final String fileName = StorageApi.resolveUniqueName(document);
        final Path filepath = Paths.get(storeDir.getAbsolutePath(), fileName);
        document.transferTo(filepath);
        return filepath.toString();
    }

    @Override
    public byte[] loadFile(final String documentURI) {
        try {
            return Files.readAllBytes(Paths.get(documentURI));
        } catch (IOException e) {
            throw new RuntimeException("Load File fail");
        }
    }

    @Override
    public void deleteFile(String documentURI) {
        try {
            if (Files.exists(Paths.get(documentURI))) {
                Files.delete(Paths.get(documentURI));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
