package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private RestTemplate restTemplate = new RestTemplate();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentAuthenticatedUser;
    private Account currentAccount;
    private TransferService transferService = new TransferService();
    private AccountServices accountServices = new AccountServices();
    private UserService userService = new UserService();

    public App() {
    }


    public static void main(String[] args)  {
        App app = new App();
        app.run();
    }

    private void run()  {
        consoleService.printGreeting();
        loginMenu();
        if (currentAuthenticatedUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentAuthenticatedUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentAuthenticatedUser = authenticationService.login(credentials);
        if (currentAuthenticatedUser == null) {
            consoleService.printErrorMessage();
            //Anne
            //Once a user has been authenticated and a token has been obtained, the logged in users account is pulled
            //from the database for usage
        } else {
            try {
                HttpEntity<Object> entity = authenticationService.getHeaders(currentAuthenticatedUser);
                currentAccount = restTemplate.exchange(API_BASE_URL + "account/getaccount/" +
                        currentAuthenticatedUser.getUser().getId(), HttpMethod.GET, entity, Account.class).getBody();
            } catch (RestClientResponseException e) {
                BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
            } catch (ResourceAccessException e) {
                BasicLogger.log(e.getMessage());
            }

        }
    }

    private void mainMenu()  {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                searchForTransfer();
            } else if (menuSelection == 5) {
                sendBucks();
            } else if (menuSelection == 6) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }
    //Anne
	private void viewCurrentBalance() {

        if (currentAccount.getBalance() != null) {
            System.out.println("Current Balance: " + currentAccount.getBalance());
        } else {
            System.err.println("Balance not found ");
        }
    }

    //Sophie
    //The transfer history shows past transactions and does not show transactions that are pending
    private void viewTransferHistory() {

        List<Transfer> transfers = transferService.viewTransferHistory(currentAuthenticatedUser, currentAccount);

        if(transfers != null && !transfers.isEmpty()) {
            consoleService.printTransferHistory(transfers);
        }else {
            System.out.println("no transactions found");
        }

    }

    //Curtis Anne Sophie - Displays pending requests and asks for approval for incoming requests
    private void viewPendingRequests() {

        List<Transfer> incoming = transferService.getPendingIncoming(currentAuthenticatedUser, currentAccount);
        //Outgoing Requests to be approved by logged in user
        List<Transfer> outgoing = transferService.getPendingOutgoing(currentAuthenticatedUser, currentAccount);

        consoleService.handlePendingOutgoing(outgoing, currentAuthenticatedUser,
                accountServices, transferService, currentAccount);
        List<Transfer> updatedOutgoing = transferService.getPendingOutgoing(currentAuthenticatedUser,currentAccount);

        consoleService.printIncomingPending(incoming);
        consoleService.printOutgoingPending(updatedOutgoing);
    }


    //Sophie
    public void sendBucks() {

        int receivingUserId = 0;
        List<User> users = userService.listOfUsers(currentAuthenticatedUser);
        while(receivingUserId == 0){
            receivingUserId = consoleService.selectUser(users, currentAuthenticatedUser.getUser());
        }
        double amountToSend = consoleService.amountToSend(receivingUserId, currentAccount);
        if (amountToSend > 0) {
            Account receivingAccount = accountServices.getAccount(currentAuthenticatedUser, receivingUserId);

            Account updatedCurrentAccount = accountServices.processTransactionsSent(currentAccount, amountToSend);
            Account updatedReceivingAccount = accountServices.processTransactionReceived(receivingAccount,amountToSend);

            accountServices.updateAccount(updatedCurrentAccount, currentAuthenticatedUser);
            accountServices.updateAccount(updatedReceivingAccount, currentAuthenticatedUser);

            Transfer transfer = new Transfer(2, 2, currentAccount.getAccountId(),
                    receivingAccount.getAccountId(), BigDecimal.valueOf(amountToSend));

            transferService.saveTransfer(transfer, currentAuthenticatedUser);

            accountServices.transactionComplete(currentAccount);
        } else {
            System.out.println("Unable to complete transaction. Please try again later.");
        }
    }

    //Anne
    private void requestBucks() {

        int requestingUserId = 0;
        List<User> users = userService.listOfUsers(currentAuthenticatedUser);

        //pull account being sent to
        while(requestingUserId == 0){
            requestingUserId = consoleService.selectUser(users, currentAuthenticatedUser.getUser());
        }
        double amountToRequest = consoleService.amountToRequest(requestingUserId);
        Account requestingAccount = accountServices.getAccount(currentAuthenticatedUser, requestingUserId);

        Transfer transfer = new Transfer(1, 1, requestingAccount.getAccountId(),
                currentAccount.getAccountId(), BigDecimal.valueOf(amountToRequest));

        transferService.saveTransfer(transfer, currentAuthenticatedUser);

        accountServices.transactionComplete(currentAccount);
    }

    public void searchForTransfer() {
        int transferID = consoleService.promptForInt("Please enter the transfer ID: ");
        Transfer transfer = transferService.getTransfer(transferID, currentAuthenticatedUser, currentAccount);

        if (transfer != null
                && (transfer.getAccountFrom() == currentAccount.getAccountId())
                || transfer.getAccountTo() == currentAccount.getAccountId()) {
            consoleService.printTransfer(transfer);
        } else {
            System.out.println("No Transaction Found with ID " + transferID + " belonging to this account.");
        }
    }
}