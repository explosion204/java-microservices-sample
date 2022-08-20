package com.epam.microserviceslearning.common.testutils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.AnythingPattern;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

public class WireMockUtils {
    public static void addGetStub(WireMockServer service, String urlRegex, byte[] responseBody) {
        service.stubFor(
                get(urlMatching(urlRegex))
                        .willReturn(aResponse().withBody(responseBody).withStatus(200))
        );
    }

    public static void addPostStub(WireMockServer service, String urlRegex, String requestBody, String responseBody) {
        service.stubFor(
                post(urlMatching(urlRegex))
                        .withRequestBody(equalTo(requestBody))
                        .willReturn(
                                aResponse()
                                        .withBody(responseBody)
                                        .withHeader("Content-Type", "application/json")
                                        .withStatus(200)
                        )
        );
    }

    public static void addPostStubForAnyRequestBody(WireMockServer service, String urlRegex, String responseBody) {
        service.stubFor(
                post(urlMatching(urlRegex))
                        .withRequestBody(new AnythingPattern())
                        .willReturn(
                                aResponse()
                                        .withBody(responseBody)
                                        .withHeader("Content-Type", "application/json")
                                        .withStatus(200)
                        )
        );
    }

    public static void addDeleteStub(WireMockServer service, String urlRegex, String responseBody) {
        service.stubFor(
                delete(urlMatching(urlRegex))
                        .willReturn(
                                aResponse()
                                        .withBody(responseBody)
                                        .withHeader("Content-Type", "application/json")
                                        .withStatus(200)
                        )
        );
    }
}
