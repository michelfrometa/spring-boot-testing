package com.example.testing.integration;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

abstract class AbstractContainerBaseTest {

    @Container
    @ServiceConnection
    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql:lts"));
        MY_SQL_CONTAINER.start();
    }
}