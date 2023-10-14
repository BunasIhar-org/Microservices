package com.itm.space.backendresources;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import dasniko.testcontainers.keycloak.*;
import io.restassured.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;
import org.keycloak.admin.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.server.*;
import static org.springframework.http.MediaType.*;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.testcontainers.junit.jupiter.*;

@Slf4j
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @Container
    static final KeycloakContainer keycloak = new KeycloakContainer().withRealmImportFile("keycloak/ITM-realm.json");

    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "realms/ITM");
    }

    protected String getBearerToken() {

        try (Keycloak keycloakAdminClient = KeycloakBuilder.builder()
                .serverUrl(keycloak.getAuthServerUrl())
                .grantType("password")
                .realm("ITM")
                .clientId("backend-gateway-client")
                .clientSecret("APpY4brglJKxFqSkdktfrtkpWPXbFh95")
                .username("user")
                .password("user")
                .build()) {

            String token = keycloakAdminClient.tokenManager().getAccessToken().getToken();

            return "Bearer " + token;
        } catch (Exception e) {
            log.error("Can't obtain an access token from Keycloak!", e);
        }
        return null;
    }

    private final ObjectWriter contentWriter = new ObjectMapper()
            .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
            .writer()
            .withDefaultPrettyPrinter();

    @Autowired
    protected MockMvc mvc;

    protected MockHttpServletRequestBuilder requestToJson(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder
                .contentType(APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder requestWithContent(MockHttpServletRequestBuilder requestBuilder,
                                                               Object content) throws JsonProcessingException {
        return requestToJson(requestBuilder).content(contentWriter.writeValueAsString(content));
    }
}