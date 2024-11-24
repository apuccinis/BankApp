/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.transactions;

import core.models.Account;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(Account sourceAccount, double amount) {
        super(sourceAccount, null, amount); // Solo fuente
    }

    @Override
    public void execute() {
        sourceAccount.withdraw(amount);
    }
}
