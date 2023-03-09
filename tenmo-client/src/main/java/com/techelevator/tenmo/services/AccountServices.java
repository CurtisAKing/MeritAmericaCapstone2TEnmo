package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class AccountServices {
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();


    public List<Account> listOfAccounts(AuthenticatedUser currentUser) {
        HttpEntity entity = authenticationService.getHeaders(currentUser);
        ResponseEntity<List<Account>> sendResponse = restTemplate.exchange(API_BASE_URL + "/account/getaccounts",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Account>>() {
                });

        return sendResponse.getBody();
    }

    public Account getAccount(AuthenticatedUser currentUser) {
        Account currentAccount = null;

        try {
            HttpEntity<Object> entity = authenticationService.getHeaders(currentUser);
            currentAccount = restTemplate.exchange(API_BASE_URL + "account/getaccount/" +
                    currentUser.getUser().getId(), HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return currentAccount;
    }

    public Account getAccountByAccountId(AuthenticatedUser currentUser, int accountId){
        Account currentAccount = null;

        try {
            HttpEntity<Object> entity = authenticationService.getHeaders(currentUser);
            currentAccount = restTemplate.exchange(API_BASE_URL + "account/getaccountbyaccountid/" +
                    accountId, HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return currentAccount;
    }

    public Account getAccount(AuthenticatedUser currentUser, int receivingUser) {
        Account recievingAccount = null;

        try {
            HttpEntity<Object> entity = authenticationService.getHeaders(currentUser);
            recievingAccount = restTemplate.exchange(API_BASE_URL + "account/getaccount/" +
                    receivingUser, HttpMethod.GET, entity, Account.class).getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return recievingAccount;
    }

    public void updateAccount(Account accountToUpdate, AuthenticatedUser authenticatedUser){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + authenticatedUser.getToken());
            HttpEntity<Account> entity = new HttpEntity<>(accountToUpdate, headers);
            restTemplate.exchange(API_BASE_URL + "account/updateaccount/",
                    HttpMethod.POST, entity, Account.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public void transactionComplete(Account currentAccount) {
        System.out.println("\nTransaction Complete\n\nNew Account Balance: " + currentAccount.getBalance());
    }

    public Account processTransactionsSent(Account currentAccount, double amountToSend){
        BigDecimal currentUserBalance = currentAccount.getBalance();
        currentAccount.setBalance(currentUserBalance.subtract(BigDecimal.valueOf(amountToSend)));
        return currentAccount;
    }

    public Account processTransactionReceived(Account receivingAccount, double amountToSend){
        BigDecimal receivingUserBalance = receivingAccount.getBalance();
        receivingAccount.setBalance(receivingUserBalance.add(BigDecimal.valueOf(amountToSend)));
        return receivingAccount;
    }
}
