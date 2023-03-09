package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Search for Transfer by ID");
        System.out.println("5: Send TE bucks");
        System.out.println("6: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public double promptForDouble(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printTransferHistory(List<Transfer> transfers) {
        for (Transfer x : transfers) {
            if (x.getTransferStatusId() != 1) {
                System.out.println(
                        "\nTransfer " + x.getTransferId() +
                                ": ");

                int type = x.getTransferTypeId();
                String transferType;
                switch (type) {
                    case 1:
                        transferType = "Sent";
                        break;
                    case 2:
                        transferType = "Received";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }

                System.out.print(transferType + " ");

                int status = x.getTransferStatusId();
                String transferStatus;
                switch (status) {
                    case 1:
                        transferStatus = "Pending";
                        break;
                    case 2:
                        transferStatus = "Approved";
                        break;
                    case 3:
                        transferStatus = "Rejected";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + status);
                }

                System.out.println(": " + transferStatus);

                System.out.println("Account From: " + x.getAccountFrom() +
                        "  Account To: " + x.getAccountTo());

                System.out.println("Amount: " + x.getAmount());
            }
        }
    }

    public int selectUser(List<User> userList, User currentUser) {
        for (User user : userList) {
            if(user.getId() != currentUser.getId()){
                System.out.println("\n" + user.getId() + ": " + user.getUsername());
            }
        }
        int x = promptForInt("Please select the number of the User you would like to transfer to.");
        if(x != currentUser.getId()){
            return x;
        } else {
            System.out.println("Please try not to send your own money to yourself.");
        }
        return 0;
    }

    public double amountToSend(int receivingUserId, Account currentAccount) {
        while (true) {
            double holding = promptForDouble("Specify an amount to send to User " + receivingUserId + ": ");
            if (holding > 0) {
                if (holding < currentAccount.getBalance().doubleValue()){
                    return holding;
                } else {
                    System.out.println("\nAmount exceeds current balance.");
                    System.out.println("Your current balance is: " + currentAccount.getBalance());
                    return 0;
                }
            }
        }

    }

    public double amountToRequest(int requestingUserId) {
        while (true) {
            double holding = promptForDouble("Specify an amount to request from User " + requestingUserId + ": ");
            if (holding > 0) {
                return holding;
            }
        }
    }

    public void printTransfer(Transfer transfer) {
        System.out.println(transfer.toString());
    }

    public List<Transfer> handlePendingOutgoing(List<Transfer> outgoing, AuthenticatedUser currentUser,
                                                AccountServices accountServices, TransferService transferService, Account currentAccount) {
        boolean failedTransaction = false;
        int failedCounter = 0;

        for (Transfer x : outgoing) {
            System.out.println(x.toString());
            int choice = promptForInt("1. Approve\n2. Reject\n3. Skip");
            failedTransaction = false;
            if (choice == 1) {
                Account accountSending = accountServices.getAccountByAccountId(currentUser, x.getAccountFrom());
                Account accountReceiving = accountServices.getAccountByAccountId(currentUser, x.getAccountTo());
                if (currentAccount.getBalance().compareTo(x.getAmount()) < 0) {
                    System.out.println("\nYou do not have enough to approve this transaction");
                    failedTransaction = true;
                    failedCounter++;

                } else {
                    accountServices.processTransactionsSent(accountSending, x.getAmount().doubleValue());
                    accountServices.processTransactionReceived(accountReceiving, x.getAmount().doubleValue());
                    x.setTransferStatusId(2);
                    transferService.updateTransfer(x, currentUser);
                    accountServices.updateAccount(accountSending, currentUser);
                    accountServices.updateAccount(accountReceiving, currentUser);
                    accountServices.transactionComplete(accountSending);
                }
            } else if (choice == 2) {
                x.setTransferStatusId(3);
                transferService.updateTransfer(x, currentUser);
                System.out.println("Transfer Request Denied");
            }
        }
        if (failedTransaction && failedCounter > 1) {
            System.out.println("\nYou had " + failedCounter +" failed request approvals due to insufficient funds");
            promptEnterKey();
        } else if (failedTransaction && failedCounter <= 1) {
            System.out.println("\nYou had 1 failed request approval due to insufficient funds.");
            promptEnterKey();
        }
        return outgoing;
    }

    private void promptEnterKey() {
        System.out.println("\nPress \"ENTER\" to view a list of remaining pending transactions...");
        scanner.nextLine();
    }

    public void printIncomingPending(List<Transfer> incoming) {
        System.out.println("\nYour Pending Requests:\n");
        if(incoming.isEmpty()){
            System.out.println("You have no outgoing requests for TEbucks pending.");
        } else{
            for (Transfer x: incoming) {
                System.out.println(x.toString());
            }
        }

    }

    public void printOutgoingPending(List<Transfer> outgoing) {
        System.out.println("\nRequests Awaiting Approval:");
        if(outgoing.isEmpty()){
            System.out.println("\nYou have no pending requests awaiting approval.");
        } else{
            for (Transfer x: outgoing) {
                System.out.println(x.toString());
            }
        }

    }
}