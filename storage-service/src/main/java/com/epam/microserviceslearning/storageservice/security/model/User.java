package com.epam.microserviceslearning.storageservice.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "app_user")
public class User {
    @Id
    private long id;
    private UserRole role;
}
