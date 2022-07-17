package com.epam.microserviceslearning.resource.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class AWSConfiguration {
    @Value("${aws.configuration.endpoint.url:}")
    private String configEndpointUrl;

    @Value("${aws.region}")
    private String region;

    @Bean
    @ConditionalOnProperty(value = "aws.configuration.endpoint.enabled", havingValue = "true")
    public AmazonS3 s3ClientFromEndpoint() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(configEndpointUrl, region)
                )
                .withPathStyleAccessEnabled(true)
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "aws.configuration.endpoint.enabled", havingValue = "false", matchIfMissing = true)
    public AmazonS3 s3ClientFromProfile() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(region)
                .build();
    }
}
