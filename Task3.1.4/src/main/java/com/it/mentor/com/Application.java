package com.it.mentor.com;

import com.it.mentor.com.model.*;
import org.springframework.http.*;
import org.springframework.web.client.*;
import java.util.*;

public class Application {

    private static final String USER_API = "http://94.198.50.185:7081/api/users";

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static void main(String[] args) {

        StringBuilder code = new StringBuilder();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response =
                REST_TEMPLATE.exchange(USER_API, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        String sessionId = Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).get(0);

        User user = new User(3L, "James", "Brown", (byte) 25);
        headers.add("Cookie", sessionId);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        response = REST_TEMPLATE.exchange(USER_API, HttpMethod.POST, entity, String.class);
        code.append(response.getBody());

        user.setName("Thomas");
        user.setLastName("Shelby");
        response = REST_TEMPLATE.exchange(USER_API, HttpMethod.PUT, entity, String.class);
        code.append(response.getBody());

        response = REST_TEMPLATE.exchange(USER_API + "/3", HttpMethod.DELETE, entity, String.class);
        code.append(response.getBody());

        System.out.println(code);
    }
}