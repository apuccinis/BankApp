/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.transactions;

import core.models.Account;

public class DepositTransaction extends Transaction {

    public DepositTransaction(Account destinationAccount, double amount) {
        super(null, destinationAccount, amount); // Solo destino
    }

    @Override
    public void execute() {
        destinationAccount.deposit(amount);
    }
}
