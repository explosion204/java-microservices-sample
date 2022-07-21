package com.epam.microserviceslearning.resource.unit;

import com.epam.microserviceslearning.resource.domain.BinaryObject;
import com.epam.microserviceslearning.resource.persistence.db.BinaryObjectRepository;
import com.epam.microserviceslearning.resource.persistence.storage.StorageService;
import com.epam.microserviceslearning.resource.service.binary.BinaryObjectService;
import com.epam.microserviceslearning.resource.service.binary.BinaryValidator;
import com.epam.microserviceslearning.resource.service.messaging.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BinaryObjectServiceTest {
    @Mock
    private BinaryObjectRepository binaryObjectRepository;

    @Spy
    private StorageService storageService;

    @Mock
    private BinaryValidator validator;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private BinaryObjectService uut;

    @Captor
    private ArgumentCaptor<String> filenameCaptor;

    @Test
    void shouldDownload() {
        // given
        final long id = 1;
        final String filename = "filename";

        final BinaryObject binaryObject = BinaryObject.builder()
                .id(id)
                .filename(filename)
                .status(BinaryObject.Status.SUCCESS)
                .build();

        when(binaryObjectRepository.findById(id)).thenReturn(Optional.of(binaryObject));
        when(storageService.read(filename)).thenReturn(InputStream.nullInputStream());

        // when
        uut.download(id);

        // then
        verify(storageService).read(filenameCaptor.capture());
        assertEquals(filename, filenameCaptor.getValue());
    }
}
