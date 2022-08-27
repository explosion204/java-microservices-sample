package com.epam.microserviceslearning.processor.integration;

import com.epam.microserviceslearning.common.logging.LoggingService;
import com.epam.microserviceslearning.common.logging.trace.AmqpTraceUtils;
import com.epam.microserviceslearning.common.testutils.TestUtils;
import com.epam.microserviceslearning.common.testutils.WireMockUtils;
import com.epam.microserviceslearning.processor.client.ResourceServiceClient;
import com.epam.microserviceslearning.processor.client.SongServiceClient;
import com.epam.microserviceslearning.processor.integration.config.BinaryProcessingHandlerTestConfiguration;
import com.epam.microserviceslearning.processor.messaging.BinaryProcessingHandler;
import com.epam.microserviceslearning.processor.model.IdDto;
import com.epam.microserviceslearning.processor.service.SongMetadataExtractor;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
        "service-gateway.url=http://localhost:8091"
})
class BinaryProcessingHandlerTest {

    @Autowired
    private WireMockServer serviceGateway;

    @Autowired
    private SongMetadataExtractor metadataExtractor;

    @Autowired
    private ResourceServiceClient resourceServiceClient;

    @Autowired
    private SongServiceClient songServiceClient;

    @Autowired
    private LoggingService logger;

    @Autowired
    private AmqpTraceUtils traceUtils;

    @Autowired
    private Gson gson;

    private BinaryProcessingHandler handler;

    @BeforeEach()
    void setUp() {
        handler = new BinaryProcessingHandler(logger, resourceServiceClient, metadataExtractor, songServiceClient, traceUtils, gson);
    }

    @SneakyThrows
    @Test
    void shouldHandleMessage() {
        // given
        final long initialId = 1;
        final IdDto initialIdDto = new IdDto();
        initialIdDto.setId(initialId);
        final Message<String> message = MessageBuilder.withPayload(gson.toJson(initialIdDto)).build();

        final long reloadedId = 2;
        final IdDto reloadedIdDto = new IdDto();
        reloadedIdDto.setId(reloadedId);

        final InputStream file = TestUtils.loadFileFromResources("test_data/sample.mp3");
        serviceGateway.resetAll();
        WireMockUtils.addGetStub(serviceGateway, "/resources/.+", file.readAllBytes());
        WireMockUtils.addPostStubForAnyRequestBody(serviceGateway, "/songs", gson.toJson(initialIdDto));
        WireMockUtils.addDeleteStub(serviceGateway, "/resources\\?id=.+", null);
        WireMockUtils.addPostStubForAnyRequestBody(serviceGateway, "/resources\\?storageType=.+", gson.toJson(reloadedIdDto));

        // when & then
        assertThatNoException().isThrownBy(() -> ReflectionTestUtils.invokeMethod(handler, "handle", message));
    }

}
