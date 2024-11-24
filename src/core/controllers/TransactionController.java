/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.transactions.*;
import core.models.Account;
import core.models.storage.StorageAccount;
import core.models.storage.StorageTransaction;

public class TransactionController {

    // Método para crear una transacción (Botón Excecute)
    public static Response createTransaction(String type, String sourceAccountId, String destinationAccountId, String amount) {
        try {
            double amountDouble = Double.parseDouble(amount);

            if (amountDouble <= 0) {
                return new Response("Amount must be greater than zero", Status.BAD_REQUEST);
            }

            StorageAccount storageAccount = StorageAccount.getInstance();
            Account sourceAccount = storageAccount.getAccount(sourceAccountId);
            Account destinationAccount = storageAccount.getAccount(destinationAccountId);

            Transaction transaction = null;

            switch (type.toUpperCase()) {
                case "DEPOSIT":
                    if (destinationAccount == null) {
                        return new Response("Destination account is required for deposit", Status.BAD_REQUEST);
                    }
                    transaction = new DepositTransaction(destinationAccount, amountDouble);
                    break;
                case "WITHDRAW":
                    if (sourceAccount == null) {
                        return new Response("Source account is required for withdrawal", Status.BAD_REQUEST);
                    }
                    if (amountDouble > sourceAccount.getBalance()) {
                        return new Response("Insufficient funds in source account", Status.BAD_REQUEST);
                    }
                    transaction = new WithdrawTransaction(sourceAccount, amountDouble);
                    break;
                case "TRANSFER":
                    if (sourceAccount == null || destinationAccount == null) {
                        return new Response("Both source and destination accounts are required for transfer", Status.BAD_REQUEST);
                    }
                    if (amountDouble > sourceAccount.getBalance()) {
                        return new Response("Insufficient funds in source account", Status.BAD_REQUEST);
                    }
                    transaction = new TransferTransaction(sourceAccount, destinationAccount, amountDouble);
                    break;
                default:
                    return new Response("Invalid transaction type", Status.BAD_REQUEST);
            }

            // Hacer la transacción
            StorageTransaction storageTransaction = StorageTransaction.getInstance();
            transaction.execute();
            storageTransaction.addTransaction(transaction);
            return new Response("Transaction successful", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error occurred", Status.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para obtener todas las transacciones RESPONSE RESPONSE REFRESH de BankFrame
    public static Response getAllTransactions() {
        try {
            StorageTransaction storageTransaction = StorageTransaction.getInstance();
            return new Response("Transactions retrieved successfully", Status.OK, storageTransaction.getTransactions());
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
