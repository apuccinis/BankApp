/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

/**
 *
 * @author User
 */
import core.models.Transaction;
import java.util.ArrayList;
public class StorageTransaction {
    // (Del Template MVC)
    // Instancia Singleton
    private static StorageTransaction instance;
    
    // Atributos del StorageTransaction
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
    
    // Método para agregar una transacción
    public boolean addTransaction(Transaction transaction) {
        // Solo se agrega
        this.transactions.add(transaction);
        return true;
    }
    
    // Método para eliminar una transacción (Template del MVC)
    public boolean delTransaction(Transaction transaction) {
        // Solo se borra
        return this.transactions.remove(transaction);
    }
    
    // Método para obtener todas las cuentas (si es necesario)
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    
}
