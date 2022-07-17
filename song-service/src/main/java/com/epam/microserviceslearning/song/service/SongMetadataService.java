package com.epam.microserviceslearning.song.service;

import com.epam.microserviceslearning.song.domain.SongMetadata;
import com.epam.microserviceslearning.song.exception.MetadataNotFoundException;
import com.epam.microserviceslearning.song.persistence.db.SongMetadataRepository;
import com.epam.microserviceslearning.song.service.model.SongMetadataDto;
import com.epam.microserviceslearning.song.service.model.SongMetadataIdDto;
import com.epam.microserviceslearning.song.service.model.SongMetadataIdListDto;
import com.epam.microserviceslearning.song.service.model.mapper.SongMetadataDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongMetadataService {
    private final SongMetadataRepository songMetadataRepository;
    private final SongMetadataDtoMapper mapper;

    public SongMetadataDto findById(long id) {
        return songMetadataRepository.findById(id)
                .map(mapper::toSongMetadataDto)
                .orElseThrow(() -> new MetadataNotFoundException(id));
    }

    public SongMetadataIdDto create(SongMetadataDto songMetadataDto) {
        final SongMetadata songMetadata = mapper.toSongMetadata(songMetadataDto);
        final SongMetadata savedSongMetadata = songMetadataRepository.save(songMetadata);

        return SongMetadataIdDto.builder()
                .id(savedSongMetadata.getId())
                .build();
    }

    public SongMetadataIdListDto delete(List<Long> ids) {
        final Map<Long, Boolean> deletionResult = new HashMap<>();
        ids.forEach(id -> deletionResult.put(id, delete(id)));

        final List<Long> deletedIds = deletionResult.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();

        return SongMetadataIdListDto.builder()
                .ids(deletedIds)
                .build();
    }

    private boolean delete(long id) {
        final Optional<SongMetadata> songMetadataOpt = songMetadataRepository.findById(id);

        if (songMetadataOpt.isEmpty()) {
            log.warn("Unable to find metadata with id = {}", id);
            return false;
        }

        songMetadataRepository.delete(songMetadataOpt.get());
        return true;
    }
}
