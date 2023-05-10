// package service.banking;

// public class Transaction implements  Runnable{
//     private final AccountManage sourceAccount;
//     private final AccountManage destinationAccount;
//     private final int cashAmount;

//     public Transaction(AccountManage sourceAccount, AccountManage destinationAccount, int cashAmount) {
//         this.sourceAccount = sourceAccount;
//         this.destinationAccount = destinationAccount;
//         this.cashAmount = cashAmount;
//     }

//     @Override
//     public void run() {
//         sourceAccount.transferTo(destinationAccount,cashAmount);
//     }
// }
