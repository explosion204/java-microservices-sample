package com.epam.microserviceslearning.resource.service.binary;

import com.epam.microserviceslearning.resource.exception.UnsupportedBinaryTypeException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class BinaryValidator {
    private static final String MP3_MIME_TYPE = "audio/mpeg";
    private final Tika tika;

    @SneakyThrows
    public void validate(InputStream file) {
        String mimeType = tika.detect(file);

        if (!mimeType.equals(MP3_MIME_TYPE)) {
            throw new UnsupportedBinaryTypeException(mimeType);
        }
    }
}
