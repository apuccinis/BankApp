/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Account;
import core.models.User;
import core.models.storage.StorageAccount;
import core.models.storage.StorageUser;
import java.util.ArrayList;
import java.util.Random;

public class AccountController {

    // Método para crear una cuenta (Botón Create)
    public static Response createAccount(String userId, String balance) {
        try {
            int userIdInt;
            double balanceDouble;

            // Validación del userId
            try {
                userIdInt = Integer.parseInt(userId);
                if (userIdInt < 0 || String.valueOf(userIdInt).length() > 9) {
                    return new Response("User ID must be a positive integer with a maximum of 9 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("User ID must be numeric", Status.BAD_REQUEST);
            }

            // Validación del balance
            try {
                balanceDouble = Double.parseDouble(balance);
                if (balanceDouble < 0) {
                    return new Response("Initial balance must not be negative", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Balance must be numeric", Status.BAD_REQUEST);
            }

            // Obtener usuario del almacenamiento
            StorageUser storageUser = StorageUser.getInstance();
            User user = storageUser.getUser(userIdInt);
            if (user == null) {
                return new Response("User not found", Status.NOT_FOUND);
            }

            // Generar ID único y aleatorio para la cuenta (método generateAccountId)
            StorageAccount storageAccount = StorageAccount.getInstance();
            String accountId;
            do {
                accountId = generateAccountId();
            } while (storageAccount.getAccount(accountId) != null);

            // Crear la cuenta y agregarla al almacenamiento
            Account account = new Account(accountId, user, balanceDouble);
            if (!storageAccount.addAccount(account)) {
                return new Response("Failed to create account", Status.INTERNAL_SERVER_ERROR);
            }

            return new Response("Account created successfully with ID: " + accountId, Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para leer una cuenta (Template Del ejemplo MVC)
    public static Response readAccount(String accountId) {
        try {
            // Validación del accountId
            if (accountId == null || accountId.trim().isEmpty()) {
                return new Response("Account ID must not be empty", Status.BAD_REQUEST);
            }

            // Obtener cuenta del almacenamiento
            StorageAccount storageAccount = StorageAccount.getInstance();
            Account account = storageAccount.getAccount(accountId);
            if (account == null) {
                return new Response("Account not found", Status.NOT_FOUND);
            }

            return new Response("Account found", Status.OK, account);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para generar un ID de cuenta aleatorio
    private static String generateAccountId() {
        Random random = new Random();
        int first = random.nextInt(1000);
        int second = random.nextInt(1000000);
        int third = random.nextInt(100);

        return String.format("%03d", first) + "-" + String.format("%06d", second) + "-" + String.format("%02d", third);
    }

    // Método para obtener todas las cuentas RESPONSE RESPONSE REFRESH de BankFrame
    public static Response getAllAccounts() {
        try {
            StorageAccount storageAccount = StorageAccount.getInstance();

            ArrayList<Account> accounts = storageAccount.getAccounts();

            accounts.sort((obj1, obj2) -> obj1.getId().compareTo(obj2.getId()));

            return new Response("Accounts retrieved successfully", Status.OK, accounts);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

}
