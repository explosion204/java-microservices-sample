package com.epam.microserviceslearning.processor.service;

import com.epam.microserviceslearning.processor.model.SongMetadataDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Year;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongMetadataExtractor {
    private static final String NAME_KEY = "dc:title";
    private static final String ARTIST_KEY = "xmpDM:artist";
    private static final String ALBUM_KEY = "xmpDM:album";
    private static final String DURATION_KEY = "xmpDM:duration";
    private static final String DATE_KEY = "xmpDM:releaseDate";

    private static final String DURATION_FORMAT = "mm:ss";

    private final Mp3Parser parser;

    public SongMetadataDto extract(InputStream file, long resourceId) {
        final Metadata metadata = parseMetadata(file);

        final String name = metadata.get(NAME_KEY);
        final String artist = metadata.get(ARTIST_KEY);
        final String album = metadata.get(ALBUM_KEY);

        final String secondsString = metadata.get(DURATION_KEY);
        final double seconds = Double.parseDouble(StringUtils.substringBefore(secondsString, "."));
        final String length = DurationFormatUtils.formatDuration((long) seconds * 1000, DURATION_FORMAT, false);

        final String dateString = metadata.get(DATE_KEY);
        final int year = Year.parse(dateString).getValue();

        return SongMetadataDto.builder()
                .resourceId(resourceId)
                .name(name)
                .artist(artist)
                .album(album)
                .length(length)
                .year(year)
                .build();
    }

    @SneakyThrows
    private Metadata parseMetadata(InputStream file) {
        final BodyContentHandler contentHandler = new BodyContentHandler();
        final Metadata metadata = new Metadata();
        final ParseContext context = new ParseContext();

        parser.parse(file, contentHandler, metadata, context);

        return metadata;
    }
}
