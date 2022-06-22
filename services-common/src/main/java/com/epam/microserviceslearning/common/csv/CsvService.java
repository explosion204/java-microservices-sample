package com.epam.microserviceslearning.common.csv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CsvService {
    @Value("${service.csv.max-length}")
    private int maxCsvLength;

    public List<Long> parse(String csv) {
        if (csv.length() > maxCsvLength) {
            throw new IllegalArgumentException(
                    String.format("Max supported CSV length: %s, but got: %s", maxCsvLength, csv.length())
            );
        }

        return Arrays.stream(csv.split(","))
                .map(Long::parseLong)
                .toList();
    }
}
