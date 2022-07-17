package com.epam.microserviceslearning.resource.integration;

import com.epam.microserviceslearning.resource.integration.config.MessageServiceTestConfiguration;
import com.epam.microserviceslearning.resource.service.messaging.MessageService;
import com.epam.microserviceslearning.resource.service.model.BinaryObjectIdDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MessageServiceTestConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "service.messaging.binary-processing-binding=binary-processing-output"
})
class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Autowired
    private OutputDestination output;

    @Autowired
    private Gson gson;

    @Test
    void shouldSendMessage() {
        // given
        final long expectedId = 1;
        final BinaryObjectIdDto idDto = BinaryObjectIdDto.builder()
                .id(expectedId)
                .build();

        // when
        messageService.sendMessage(idDto);

        // then
        assertThat(getIdFromQueue()).isEqualTo(expectedId);
    }

    public long getIdFromQueue() {
        final byte[] payload = output.receive().getPayload();
        final String json = new String(payload, StandardCharsets.UTF_8);
        final BinaryObjectIdDto idDto = gson.fromJson(json, BinaryObjectIdDto.class);

        return idDto.getId();
    }
}
