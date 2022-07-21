package com.epam.microserviceslearning.resource.contract;

import com.epam.microserviceslearning.resource.ResourceServiceApplication;
import com.epam.microserviceslearning.resource.controller.BinaryObjectController;
import com.epam.microserviceslearning.resource.service.binary.BinaryObjectService;
import com.epam.microserviceslearning.resource.service.model.BinaryObjectIdDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ResourceServiceApplication.class)
public class BaseContractTest {
    @Autowired
    private BinaryObjectController controller;

    @MockBean
    private BinaryObjectService binaryObjectService;

    @BeforeEach
    void setUp() {
        final BinaryObjectIdDto idDto = BinaryObjectIdDto.builder()
                .id(1)
                .build();

        when(binaryObjectService.save(any())).thenReturn(idDto);
    }
}
