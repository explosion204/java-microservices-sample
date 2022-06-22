package com.epam.microserviceslearning.resource.controller;

import com.epam.microserviceslearning.common.csv.CsvService;
import com.epam.microserviceslearning.resource.service.model.BinaryObjectIdDto;
import com.epam.microserviceslearning.resource.service.model.BinaryObjectIdListDto;
import com.epam.microserviceslearning.resource.service.binary.BinaryObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class BinaryObjectController {
    private final CsvService csvService;
    private final BinaryObjectService binaryObjectService;

    @PostMapping
    public ResponseEntity<BinaryObjectIdDto> upload(InputStream file) {
        final BinaryObjectIdDto binaryObjectIdDto = binaryObjectService.save(file);
        return ResponseEntity.ok(binaryObjectIdDto);
    }

    @GetMapping(value = "{id}", produces = "application/octet-stream")
    public ResponseEntity<Resource> download(@PathVariable("id") long id) {
        final byte[] data = binaryObjectService.download(id);
        final ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .contentLength(data.length)
                .body(resource);
    }

    @DeleteMapping
    public ResponseEntity<BinaryObjectIdListDto> delete(@RequestParam("id") String idsCsv) {
        final List<Long> ids = csvService.parse(idsCsv);
        final BinaryObjectIdListDto deletedIds = binaryObjectService.delete(ids);
        return ResponseEntity.ok(deletedIds);
    }
}
