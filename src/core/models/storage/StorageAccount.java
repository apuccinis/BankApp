/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;
/**
 *
 * @author User
 */
import core.models.Account;
import java.util.ArrayList;

public class StorageAccount {
    // (Del Template MVC)
    // Instancia Singleton
    private static StorageAccount instance;
    
    // Atributos del StorageAccount
    private ArrayList<Account> accounts;
    
    private StorageAccount() {
        this.accounts = new ArrayList<>();
    }
    
    public static StorageAccount getInstance() {
        if (instance == null) {
            instance = new StorageAccount();
        }
        return instance;
    }
    
    // Método para agregar una cuenta
    public boolean addAccount(Account account) {
        for (Account acc : this.accounts) {
            if (acc.getId().equals(account.getId())) {
                return false;
            }
        }
        this.accounts.add(account);
        return true;
    }
    
    // Método para obtener una cuenta por su ID
    public Account getAccount(String accountId) {
        for (Account account : this.accounts) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }
    
    // Método para eliminar una cuenta por su ID
    public boolean delAccount(String accountId) {
        for (Account account : this.accounts) {
            if (account.getId().equals(accountId)) {
                this.accounts.remove(account);
                return true;
            }
        }
        return false;
    }
    
    // Método para obtener todas las cuentas (si es necesario)
    public ArrayList<Account> getAllAccounts() {
        return new ArrayList<>(this.accounts);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    
    
}
