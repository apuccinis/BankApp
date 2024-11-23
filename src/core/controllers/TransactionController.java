/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;
/**
 *
 * @author User
 */
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Transaction;
import core.models.TransactionType;
import core.models.Account;
import core.models.storage.StorageAccount;
import core.models.storage.StorageTransaction;
import java.util.ArrayList;
import java.util.Collections;

public class TransactionController {

    // Método para crear una transacción (Botón Excecute)
    public static Response createTransaction(String type, String sourceAccountId, String destinationAccountId, String amount) {
        try {
            double amountDouble;

            // Validación tipo de transacción no nulo o vacío (ES POR SI ACASO/Pq el jCombo siempre tiene algo)
            if (type == null || type.trim().isEmpty()) {
                return new Response("Transaction type must not be empty", Status.BAD_REQUEST);
            }

            // Validación convertir type a ENUM
            TransactionType transactionType;
            try {
                transactionType = TransactionType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return new Response("Invalid transaction type", Status.BAD_REQUEST);
            }

            // Validación amount no vacío y numérico
            if (amount == null || amount.trim().isEmpty()) {
                return new Response("Amount must not be empty", Status.BAD_REQUEST);
            }

            try {
                amountDouble = Double.parseDouble(amount);
                if (amountDouble <= 0) {
                    return new Response("Amount must be greater than zero", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Amount must be numeric", Status.BAD_REQUEST);
            }

            // Obtener las cuentas del almacenamiento
            StorageAccount storageAccount = StorageAccount.getInstance();
            Account sourceAccount = null;
            Account destinationAccount = null;

            if (sourceAccountId != null && !sourceAccountId.isEmpty()) {
                sourceAccount = storageAccount.getAccount(sourceAccountId);
            }
            if (destinationAccountId != null && !destinationAccountId.isEmpty()) {
                destinationAccount = storageAccount.getAccount(destinationAccountId);
            }

            // Validaciones específicas para cada type de transacción (Requerimientos)
            switch (transactionType) {
                case DEPOSIT:
                    if (destinationAccount == null) {
                        return new Response("Destination account is required for deposit", Status.BAD_REQUEST);
                    }
                    break;
                case WITHDRAW:
                    if (sourceAccount == null) {
                        return new Response("Source account is required for withdrawal", Status.BAD_REQUEST);
                    }
                    if (amountDouble > sourceAccount.getBalance()) {
                        return new Response("Insufficient funds in source account", Status.BAD_REQUEST);
                    }
                    break;
                case TRANSFER:
                    if (sourceAccount == null || destinationAccount == null) {
                        return new Response("Both source and destination accounts are required for transfer", Status.BAD_REQUEST);
                    }
                    if (amountDouble > sourceAccount.getBalance()) {
                        return new Response("Insufficient funds in source account", Status.BAD_REQUEST);
                    }
                    break;
                default:
                    return new Response("Invalid transaction type", Status.BAD_REQUEST);
            }

            // Procesar la transacción
            StorageTransaction storageTransaction = StorageTransaction.getInstance();
            switch (transactionType) {
                case DEPOSIT:
                    destinationAccount.deposit(amountDouble);
                    storageTransaction.addTransaction(new Transaction(transactionType, null, destinationAccount, amountDouble));
                    return new Response("Deposit successful", Status.CREATED);

                case WITHDRAW:
                    if (sourceAccount.withdraw(amountDouble)) {
                        storageTransaction.addTransaction(new Transaction(transactionType, sourceAccount, null, amountDouble));
                        return new Response("Withdrawal successful", Status.CREATED);
                    }
                    break;

                case TRANSFER:
                    if (sourceAccount.withdraw(amountDouble)) {
                        destinationAccount.deposit(amountDouble);
                        storageTransaction.addTransaction(new Transaction(transactionType, sourceAccount, destinationAccount, amountDouble));
                        return new Response("Transfer successful", Status.CREATED);
                    }
                    break;
            }

            return new Response("Transaction failed", Status.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {
            return new Response("Unexpected error occurred", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Método para obtener todas las transacciones RESPONSE RESPONSE REFRESH de BankFrame
    public static Response getAllTransactions() {
        try {
            StorageTransaction storageTransaction = StorageTransaction.getInstance();

            ArrayList<Transaction> transactions = new ArrayList<>(storageTransaction.getTransactions());

            Collections.reverse(transactions);

            return new Response("Transactions retrieved successfully", Status.OK, transactions);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
}
