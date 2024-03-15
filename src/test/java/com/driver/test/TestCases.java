package com.driver.test;

import com.driver.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestCases {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        this.baseUrl = "http://localhost:" + port + "/pg";
    }

    @Test
    void testRegisterPG() {
        PGDetailsRequest request = new PGDetailsRequest("TestPG", 1500.0, 10);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/register", request, String.class);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Registered PG with ID: 1", response.getBody());
    }

    @Test
    void testFetchDetailsForNonexistentPG() {
        // Fetch details for a PG that doesn't exist (ID 100)
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/details/{id}", String.class, 100);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("PG with ID 100 not found.", response.getBody());
    }
}