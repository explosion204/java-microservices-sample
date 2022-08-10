package com.epam.microserviceslearning.common.storage;

import com.epam.microserviceslearning.common.storage.exception.StorageException;

import java.io.InputStream;

public interface Storage {
    void store(InputStream file, String path) throws StorageException;
    InputStream read(String path);
    void delete(String path);
}
