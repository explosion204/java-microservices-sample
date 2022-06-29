package com.epam.microserviceslearning.resource.persistence.model.mapper;

import com.epam.microserviceslearning.resource.domain.BinaryObject;
import com.epam.microserviceslearning.resource.persistence.model.BinaryObjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BinaryObjectEntityMapper {
    BinaryObject toBinaryObject(BinaryObjectEntity binaryObjectEntity);
    BinaryObjectEntity toBinaryObjectEntity(BinaryObject binaryObject);
}
