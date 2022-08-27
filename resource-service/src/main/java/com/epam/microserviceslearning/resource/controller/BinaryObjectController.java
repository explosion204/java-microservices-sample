package com.epam.microserviceslearning.resource.controller;

import com.epam.microserviceslearning.common.csv.CsvService;
import com.epam.microserviceslearning.common.logging.LoggingService;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import com.epam.microserviceslearning.resource.service.binary.BinaryObjectService;
import com.epam.microserviceslearning.resource.service.model.BinaryObjectIdDto;
import com.epam.microserviceslearning.resource.service.model.BinaryObjectIdListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class BinaryObjectController {
    private final LoggingService logger;
    private final CsvService csvService;
    private final BinaryObjectService binaryObjectService;

    @PostMapping
    @ResponseStatus(OK)
    public BinaryObjectIdDto upload(
            @RequestParam(name = "storageType", defaultValue = "staging") StorageType storageType,
            @RequestBody byte[] bytes
    ) {
        logger.info("Received upload request for %s storage", storageType.name());

        final InputStream file = new ByteArrayInputStream(bytes);
        final BinaryObjectIdDto result = binaryObjectService.save(file, storageType);

        logger.info("File is uploaded to %s storage", storageType.name());

        return result;
    }

    @GetMapping(value = "{id}", produces = "audio/mpeg")
    public ResponseEntity<Resource> download(@PathVariable("id") long id) {
        logger.info("Received download request for id = %s", id);

        final byte[] data = binaryObjectService.download(id);
        final ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .contentLength(data.length)
                .body(resource);
    }

    @DeleteMapping
    @ResponseStatus(OK)
    public BinaryObjectIdListDto delete(@RequestParam("id") String idsCsv) {
        final List<Long> ids = csvService.parse(idsCsv);
        logger.info("Received delete request for ids = %s", idsCsv);
        return binaryObjectService.delete(ids);
    }
}
