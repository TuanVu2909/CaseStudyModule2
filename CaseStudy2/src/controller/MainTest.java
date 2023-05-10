package controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import ioFile.UserFileIO;
import model.LoginResponse;
import model.User;
import service.banking.AccountManager;
import service.banking.BalanceRefresher;
import service.implUser.UserManage;

public class MainTest {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        UserManage userManage = new UserManage(scanner);
        AccountManager accountManager = new AccountManager();



        // Start balance refresher thread
        BalanceRefresher balanceRefresher = new BalanceRefresher(accountManager);

        balanceRefresher.start();


        List<User> users = UserFileIO.loadUsers();

        for (User user : users) {
            accountManager.createUser(user.getFullName(), user.getPhone(), user.getPassword(), user.getEmail(),
                    user.getBalance());
        }
        int choice;
        do {
            System.out.println("MENU");
            System.out.println("1. Register ");
            System.out.println("2. Login ");
            System.out.println("3. Display ");
            System.out.println("0. Exit ");
            System.out.println("Enter Choice ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    userManage.register(accountManager);
                    break;
                case 2:
                    LoginResponse response = userManage.login();
                    if (response.isLoginSuccess()) {
                        System.out.println("Login Success!");
                        do {
                            System.out.println("SubMenu: ");
                            System.out.println("1.Deposit CashAmount: ");
                            System.out.println("2: Withdraw CashAmount ");
                            System.out.println("3: Transfer CashAmount ");
                            System.out.println("4: Show balance ");
                            System.out.println("0: Logout ");
                            choice = Integer.parseInt(scanner.nextLine());
                            switch (choice) {
                                case 1:
                                    double amount = 0;
                                    System.out.println("Nhập số tiền nạp: ");
                                    amount = Double.parseDouble(scanner.nextLine());
                                    try {
                                        accountManager.deposit(response.getPhone(), amount);
                                    } catch (Exception e) {
                                        System.out.println("Lỗi: " + e.getMessage());

                                    }
                                    double balance = accountManager.getBalance(response.getPhone());
                                    System.out.printf("Account: " + response.getPhone() + " " + "balance: " + balance + "\n");
                                    break;
                                case 2:
                                    double amountW = 0;
                                    System.out.println("Nhập số tiền rút: ");
                                    amountW = Double.parseDouble(scanner.nextLine());
                                    try {
                                        accountManager.withdraw(response.getPhone(), amountW);
                                    } catch (Exception e) {
                                        System.out.println("Lỗi: " + e.getMessage());

                                    }
                                    double balanceW = accountManager.getBalance(response.getPhone());
                                    System.out.printf("Account: " + response.getPhone() + " " + "balance: " + balanceW + "\n");
                                    break;
                                case 3:
                                    double amountTransfer = 0;
                                    String toPhoneNumber;
                                    System.out.println("Nhập số tài khoản chuyển: ");
                                    toPhoneNumber = scanner.nextLine();

                                    System.out.println("Nhập số tiền chuyển: ");
                                    amountTransfer = Double.parseDouble(scanner.nextLine());
                                    try {
                                        accountManager.transfer(response.getPhone(), toPhoneNumber, amountTransfer);
                                    } catch (Exception e) {
                                        System.out.println("Lỗi: " + e.getMessage());

                                    }
                                    double balanceTransfer = accountManager.getBalance(response.getPhone());
                                    System.out.printf("Account :" + response.getPhone() + " " + "balance: " + balanceTransfer + "\n");

                                    break;
                                case 4:
                                    double balance2 = accountManager.getBalance(response.getPhone());
                                    System.out.printf("Account: " + response.getPhone() + " " + "balance: " + balance2 + "\n");
                                    break;
                            }
                        } while (choice != 0);
                    }
                    break;
                case 3:
                    userManage.display();
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        } while (true);
    }

    // public static void main2(String[] args) throws IOException {
    // AccountManager accountManager = new AccountManager();

    // List<User> users = UserFileIO.loadUsers();
    // for (User user : users) {
    // accountManager.createUser(user.getFullName(), user.getPhone(),
    // user.getPassword(), user.getEmail(),
    // user.getBalance());
    // }
    // // Start balance refresher thread
    // BalanceRefresher balanceRefresher = new BalanceRefresher(accountManager);
    // balanceRefresher.start();

    // // Perform transactions
    // accountManager.deposit("0329343811", 10);
    // accountManager.deposit("0329343811", 15);
    // // accountManager.withdraw("0909106986", 25);

    // // Get account balances
    // // double balance001 = accountManager.getBalance("0909106986");
    // double balance002 = accountManager.getBalance("0329343811");

    // // System.out.printf("Account 0909106986 balance: " +
    // // String.valueOf(balance001));
    // System.out.printf("Account 0329343811 balance: " +
    // String.valueOf(balance002));
    // }

}
