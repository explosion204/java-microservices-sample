package com.epam.microserviceslearning.resource.persistence.db.impl;

import com.epam.microserviceslearning.resource.domain.BinaryObject;
import com.epam.microserviceslearning.resource.persistence.db.BinaryObjectRepository;
import com.epam.microserviceslearning.resource.persistence.model.BinaryObjectEntity;
import com.epam.microserviceslearning.resource.persistence.model.mapper.BinaryObjectEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BinaryObjectRepositoryImpl implements BinaryObjectRepository {
    private final BinaryObjectEntityJpaRepository jpaRepository;
    private final BinaryObjectEntityMapper mapper;

    @Override
    public Optional<BinaryObject> findById(long id) {
        final Optional<BinaryObjectEntity> entity = jpaRepository.findById(id);

        if (entity.isPresent()) {
            final BinaryObject binaryObject = mapper.toBinaryObject(entity.get());
            return Optional.of(binaryObject);
        }

        return Optional.empty();
    }

    @Override
    public BinaryObject save(BinaryObject binaryObject) {
        final BinaryObjectEntity entity = mapper.toBinaryObjectEntity(binaryObject);
        final BinaryObjectEntity updatedEntity = jpaRepository.save(entity);
        return mapper.toBinaryObject(updatedEntity);
    }
}
