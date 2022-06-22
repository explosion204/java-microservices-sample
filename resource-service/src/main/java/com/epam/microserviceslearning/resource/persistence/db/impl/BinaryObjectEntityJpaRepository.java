package com.epam.microserviceslearning.resource.persistence.db.impl;

import com.epam.microserviceslearning.resource.persistence.model.BinaryObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinaryObjectEntityJpaRepository extends JpaRepository<BinaryObjectEntity, Long> {

}
