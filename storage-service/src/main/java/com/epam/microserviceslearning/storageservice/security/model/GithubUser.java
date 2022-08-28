package com.epam.microserviceslearning.storageservice.security.model;

import lombok.Data;

@Data
public class GithubUser {
    private String login;
    private long id;
}
