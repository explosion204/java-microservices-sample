package com.epam.microserviceslearning.storageservice.controller;

import com.epam.microserviceslearning.common.csv.CsvService;
import com.epam.microserviceslearning.common.logging.LoggingService;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.storageservice.service.StorageService;
import com.epam.microserviceslearning.storageservice.service.model.StorageMetadataDto;
import com.epam.microserviceslearning.storageservice.service.model.StorageMetadataIdDto;
import com.epam.microserviceslearning.storageservice.service.model.StorageMetadataIdListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/storages")
@RequiredArgsConstructor
@Validated
public class StorageMetadataController {
    private final LoggingService logger;
    private final StorageService storageService;
    private final CsvService csvService;

    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public StorageMetadataIdDto createStorage(@Valid @RequestBody StorageMetadataDto storageMetadataDto) {
        return storageService.create(storageMetadataDto);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<StorageMetadataDto> findAllStorages() {
        return storageService.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public StorageMetadataDto findStorageById(@PathVariable("id") long id) {
        logger.info("Received find request for id = %s", id);
        return storageService.findById(id);
    }

    @GetMapping("random/{type}")
    @ResponseStatus(OK)
    public StorageMetadataDto findRandomStorage(@PathVariable("type") StorageType type) {
        logger.info("Received find request for type = %s", type.name());
        return storageService.findRandomByType(type);
    }

    @DeleteMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public StorageMetadataIdListDto deleteStorage(@RequestParam("id") String idsCsv) {
        final List<Long> ids = csvService.parse(idsCsv);
        return storageService.delete(ids);
    }
}
