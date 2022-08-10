package com.epam.microserviceslearning.storageservice.persistence.model;

import com.epam.microserviceslearning.common.storage.factory.StorageProvider;
import com.epam.microserviceslearning.common.storage.factory.StorageType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "storage_metadata")
@Data
public class StorageMetadataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private long id;

    @Column(name = "type")
    private StorageType type;

    @Column(name = "provider")
    private StorageProvider provider;

    @Column(name = "descriptor")
    private String descriptor;
}
