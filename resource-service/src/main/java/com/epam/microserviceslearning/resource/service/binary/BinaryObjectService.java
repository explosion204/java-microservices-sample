package com.epam.microserviceslearning.resource.service.binary;

import com.epam.microserviceslearning.resource.domain.BinaryObject;
import com.epam.microserviceslearning.resource.exception.BinaryDeletedException;
import com.epam.microserviceslearning.resource.exception.BinaryNotFoundException;
import com.epam.microserviceslearning.resource.exception.BinaryUploadException;
import com.epam.microserviceslearning.resource.persistence.db.BinaryObjectRepository;
import com.epam.microserviceslearning.resource.persistence.storage.StorageService;
import com.epam.microserviceslearning.resource.service.messaging.MessageService;
import com.epam.microserviceslearning.resource.service.model.BinaryObjectIdDto;
import com.epam.microserviceslearning.resource.service.model.BinaryObjectIdListDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.epam.microserviceslearning.resource.domain.BinaryObject.Status.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class BinaryObjectService {
    private final BinaryObjectRepository binaryObjectRepository;
    private final StorageService storageService;
    private final BinaryValidator validator;
    private final MessageService messageService;

    @SneakyThrows
    public byte[] download(long id) {
        final BinaryObject binaryObject = binaryObjectRepository.findById(id)
                .orElseThrow(() -> new BinaryNotFoundException(id));

        if (binaryObject.getStatus() == BinaryObject.Status.FAILED) {
            throw new BinaryNotFoundException(id);
        }

        if (binaryObject.getStatus() == BinaryObject.Status.DELETED) {
            throw new BinaryDeletedException(id);
        }

        final String filename = binaryObject.getFilename();
        final InputStream inputStream = storageService.read(filename);

        return IOUtils.toByteArray(inputStream);
    }

    @Transactional
    public BinaryObjectIdDto save(InputStream file) {
        validator.validate(file);

        final String filename = UUID.randomUUID().toString();
        BinaryObject.Status status = SUCCESS;

        try {
            storageService.store(file, filename);
        } catch (BinaryUploadException e) {
            status = BinaryObject.Status.FAILED;
            log.error("An error occurred during upload of file '{}', cause: {}", filename, e.getCause());
        }

        final BinaryObject newObject = BinaryObject.builder()
                .filename(filename)
                .status(status)
                .build();

        final BinaryObject savedObject = binaryObjectRepository.save(newObject);
        sendMessage(savedObject);

        return BinaryObjectIdDto.builder()
                .id(savedObject.getId())
                .build();
    }

    @Transactional
    public BinaryObjectIdListDto delete(List<Long> ids) {
        final Map<Long, Boolean> deletionResult = new HashMap<>();
        ids.forEach(id -> deletionResult.put(id, delete(id)));

        final List<Long> deletedIds = deletionResult.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();

        return BinaryObjectIdListDto.builder()
                .ids(deletedIds)
                .build();
    }

    private boolean delete(long id) {
        final Optional<BinaryObject> binaryObjectOpt = binaryObjectRepository.findById(id);

        if (binaryObjectOpt.isEmpty()) {
            log.warn("Unable to find binary with id = {}", id);
            return false;
        }

        final BinaryObject binaryObject = binaryObjectOpt.get();

        if (binaryObject.getStatus() == BinaryObject.Status.DELETED) {
            log.warn("Binary with id = {} is already deleted", id);
            return false;
        }

        final String filename = binaryObject.getFilename();
        storageService.delete(filename);

        binaryObject.setStatus(BinaryObject.Status.DELETED);
        binaryObjectRepository.save(binaryObject);

        return true;
    }

    private void sendMessage(BinaryObject binaryObject) {
        if (binaryObject.getStatus() == SUCCESS) {
            messageService.sendMessage(binaryObject.getId());

        } else {
            log.warn("Last upload wasn't successful, message is not sent");
        }
    }
}
