package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserService {
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<User> listOfUsers(AuthenticatedUser currentUser){
        HttpEntity entity = authenticationService.getHeaders(currentUser);
        ResponseEntity<List<User>> sendResponse = restTemplate.exchange(API_BASE_URL + "user/getusers",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<User>>() {});
        return sendResponse.getBody();
    }
}
