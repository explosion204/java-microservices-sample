package com.epam.microserviceslearning.resource.persistence.db;

import com.epam.microserviceslearning.resource.domain.BinaryObject;

import java.util.Optional;

public interface BinaryObjectRepository {
    Optional<BinaryObject> findById(long id);
    BinaryObject save(BinaryObject binaryObject);
}
