package com.epam.microserviceslearning.song.controller;

import com.epam.microserviceslearning.common.csv.CsvService;
import com.epam.microserviceslearning.song.service.SongMetadataService;
import com.epam.microserviceslearning.song.service.model.SongMetadataDto;
import com.epam.microserviceslearning.song.service.model.SongMetadataIdDto;
import com.epam.microserviceslearning.song.service.model.SongMetadataIdListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
@Slf4j
public class SongController {
    private final CsvService csvService;
    private final SongMetadataService songMetadataService;

    @PostMapping
    public ResponseEntity<SongMetadataIdDto> createSongMetadata(@Valid @RequestBody SongMetadataDto songMetadataDto) {
        final SongMetadataIdDto songMetadataIdDto = songMetadataService.create(songMetadataDto);
        log.info("Saved metadata: {}", songMetadataDto);
        return ResponseEntity.ok(songMetadataIdDto);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<SongMetadataDto> findSongMetadata(@PathVariable("id") long id) {
        final SongMetadataDto songMetadataDto = songMetadataService.findById(id);
        return ResponseEntity.ok(songMetadataDto);
    }

    @DeleteMapping
    public ResponseEntity<SongMetadataIdListDto> deleteSongMetadata(@RequestParam("id") String idsCsv) {
        final List<Long> ids = csvService.parse(idsCsv);
        final SongMetadataIdListDto deletedIds = songMetadataService.delete(ids);
        return ResponseEntity.ok(deletedIds);
    }
}
