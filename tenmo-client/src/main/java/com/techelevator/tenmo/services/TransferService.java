package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final ConsoleService consoleService = new ConsoleService();

    //Sophie
    public List<Transfer> viewTransferHistory(AuthenticatedUser currentUser, Account currentAccount) {

        HttpEntity<Object> entity = authenticationService.getHeaders(currentUser);
        ResponseEntity<List<Transfer>> transferResponse =
                restTemplate.exchange(API_BASE_URL + "transfer/history/" + currentAccount.getAccountId(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<Transfer>>() {
                        });
        List<Transfer> transfers = transferResponse.getBody();


        return transfers;
    }

    public void saveTransfer(Transfer transferToUpdate, AuthenticatedUser authenticatedUser) {
        int id = 0;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + authenticatedUser.getToken());
            HttpEntity<Transfer> entity = new HttpEntity<>(transferToUpdate, headers);
            restTemplate.exchange(API_BASE_URL + "transfer/savetransfer/",
                    HttpMethod.POST, entity, Transfer.class).getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public Transfer getTransfer(int transferId, AuthenticatedUser currentUser, Account currentAccount) {
        Transfer transfer = null;

        try {
            HttpEntity<Object> entity = authenticationService.getHeaders(currentUser);
            transfer = restTemplate.exchange(API_BASE_URL + "transfer/gettransfer/" +
                    transferId, HttpMethod.GET, entity, Transfer.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfer;
    }

    public List<Transfer> getPendingIncoming(AuthenticatedUser currentUser, Account currentAccount) {

        HttpEntity<Object> entity = authenticationService.getHeaders(currentUser);
        ResponseEntity<List<Transfer>> transferResponse =
                restTemplate.exchange(API_BASE_URL + "transfer/pendingincoming/" + currentAccount.getAccountId(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<Transfer>>() {
                        });
        List<Transfer> transfers = transferResponse.getBody();

        return transfers;
    }

    public List<Transfer> getPendingOutgoing(AuthenticatedUser currentUser, Account currentAccount) {

        HttpEntity<Object> entity = authenticationService.getHeaders(currentUser);
        ResponseEntity<List<Transfer>> transferResponse =
                restTemplate.exchange(API_BASE_URL + "transfer/pendingoutgoing/" + currentAccount.getAccountId(),
                        HttpMethod.GET, entity, new ParameterizedTypeReference<List<Transfer>>() {
                        });
        List<Transfer> transfers = transferResponse.getBody();

        return transfers;
    }

    public void updateTransfer(Transfer transferToUpdate, AuthenticatedUser authenticatedUser) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + authenticatedUser.getToken());
            HttpEntity<Transfer> entity = new HttpEntity<>(transferToUpdate, headers);
            restTemplate.exchange(API_BASE_URL + "transfer/updatetransfer/",
                    HttpMethod.POST, entity, Transfer.class).getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }
}
