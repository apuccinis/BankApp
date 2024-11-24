/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

/**
 *
 * @author User
 */
import core.models.transactions.Transaction;
import java.util.ArrayList;

public class StorageTransaction {

    // (Del Template MVC)
    // Instancia Singleton
    private static StorageTransaction instance;

    private ArrayList<Transaction> transactions;

    private StorageTransaction() {
        this.transactions = new ArrayList<>();
    }

    public static StorageTransaction getInstance() {
        if (instance == null) {
            instance = new StorageTransaction();
        }
        return instance;
    }

    public boolean addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        return true;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

}
