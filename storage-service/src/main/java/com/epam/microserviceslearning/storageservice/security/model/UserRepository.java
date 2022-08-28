package com.epam.microserviceslearning.storageservice.security.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
