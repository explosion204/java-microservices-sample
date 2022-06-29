package com.epam.microserviceslearning.resource.persistence.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "binary_object")
@Data
public class BinaryObjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "status")
    private String status;
}
