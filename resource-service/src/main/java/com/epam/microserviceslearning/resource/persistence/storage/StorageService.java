package com.epam.microserviceslearning.resource.persistence.storage;

import com.epam.microserviceslearning.resource.exception.BinaryUploadException;

import java.io.InputStream;

public interface StorageService {
    void store(InputStream file, String filename) throws BinaryUploadException;
    InputStream read(String filename);
    void delete(String filename);
}
