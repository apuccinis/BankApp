/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.transactions;

import core.models.Account;

public class TransferTransaction extends Transaction {

    public TransferTransaction(Account sourceAccount, Account destinationAccount, double amount) {
        super(sourceAccount, destinationAccount, amount);
    }

    @Override
    public void execute() {
        sourceAccount.withdraw(amount);
        destinationAccount.deposit(amount);
    }
}
