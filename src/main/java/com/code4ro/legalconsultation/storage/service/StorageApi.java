package com.code4ro.legalconsultation.storage.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public interface StorageApi {

    static String resolveUniqueName(final MultipartFile document) {
        return RandomStringUtils.randomAlphabetic(10) +
                StringUtils.cleanPath(Objects.requireNonNull(document.getOriginalFilename()));
    }

    String storeFile(final MultipartFile document) throws Exception;

    byte[] loadFile(String documentURI);

    void deleteFile(String documentURI);
}
