package com.epam.microserviceslearning.processor.integration;

import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.processor.client.ResourceServiceClient;
import com.epam.microserviceslearning.processor.client.SongServiceClient;
import com.epam.microserviceslearning.processor.integration.config.BinaryProcessingHandlerTestConfiguration;
import com.epam.microserviceslearning.processor.messaging.BinaryProcessingHandler;
import com.epam.microserviceslearning.processor.model.IdDto;
import com.epam.microserviceslearning.processor.service.SongMetadataExtractor;
import com.epam.microserviceslearning.processor.utils.WireMockUtils;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThatNoException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BinaryProcessingHandlerTestConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "resource-service.url=http://localhost:8070",
        "song-service.url=http://localhost:8071"
})
class BinaryProcessingHandlerTest {

    @Autowired
    @Qualifier("resource-service")
    private WireMockServer resourceService;

    @Autowired
    @Qualifier("song-service")
    private WireMockServer songService;

    @Autowired
    private SongMetadataExtractor metadataExtractor;

    @Autowired
    private ResourceServiceClient resourceServiceClient;

    @Autowired
    private SongServiceClient songServiceClient;

    @Autowired
    private Gson gson;

    private BinaryProcessingHandler handler;

    @BeforeEach()
    void setUp() {
        handler = new BinaryProcessingHandler(resourceServiceClient, metadataExtractor, songServiceClient, gson);
    }

    @SneakyThrows
    @Test
    void test() {
        // given
        final long initialId = 1;
        final IdDto idDto = new IdDto();
        idDto.setId(initialId);
        final Message<String> message = MessageBuilder.withPayload(gson.toJson(idDto)).build();

        final InputStream file = TestUtils.loadFileFromResources("test_data/sample.mp3");
        WireMockUtils.addGetStub(resourceService, "/resources/.+", file.readAllBytes());
        WireMockUtils.addPostStubForAnyRequestBody(songService, "/songs", gson.toJson(idDto));

        // when & then
        assertThatNoException().isThrownBy(() -> ReflectionTestUtils.invokeMethod(handler, "handle", message));
    }

}
