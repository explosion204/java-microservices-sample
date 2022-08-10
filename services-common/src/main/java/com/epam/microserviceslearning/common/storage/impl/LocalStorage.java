package com.epam.microserviceslearning.common.storage.impl;

import com.epam.microserviceslearning.common.storage.Storage;
import com.epam.microserviceslearning.common.storage.exception.StorageException;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LocalStorage implements Storage {
    public static final long LOCAL_STORAGE_ID = -1;

    private final String basePath;

    @SneakyThrows
    public LocalStorage(String basePath) {
        this.basePath = basePath;
        Files.createDirectories(Paths.get(basePath));
    }


    @SneakyThrows
    @Override
    public void store(InputStream file, String path) {
        final Path fullPath = Paths.get(basePath, path);
        Files.write(fullPath, IOUtils.toByteArray(file), StandardOpenOption.CREATE);
    }

    @SneakyThrows
    @Override
    public InputStream read(String path) {
        final Path fullPath = Paths.get(basePath, path);
        return Files.newInputStream(fullPath, StandardOpenOption.READ);
    }

    @SneakyThrows
    @Override
    public void delete(String path) {
        final Path fullPath = Paths.get(basePath, path);
        Files.deleteIfExists(fullPath);
    }
}
