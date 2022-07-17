package com.epam.microserviceslearning.resource.unit;

import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.resource.exception.UnsupportedBinaryTypeException;
import com.epam.microserviceslearning.resource.service.binary.BinaryValidator;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BinaryValidatorTest {
    private static BinaryValidator validator;

    @BeforeAll
    static void setUp() {
        final Tika tika = new Tika();
        validator = new BinaryValidator(tika);
    }

    @Test
    @SneakyThrows
    void shouldNotThrowValidationException() {
        @Cleanup final InputStream file = TestUtils.loadFileFromResources("test_data/valid.mp3");
        validator.validate(file);
        assertThatNoException();
    }

    @Test
    @SneakyThrows
    void shouldThrowValidationException() {
        @Cleanup final InputStream file = TestUtils.loadFileFromResources("test_data/invalid.mp3");
        assertThatThrownBy(() -> validator.validate(file))
                .isInstanceOf(UnsupportedBinaryTypeException.class);
    }
}
