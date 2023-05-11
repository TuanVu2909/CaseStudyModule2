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

        // Khởi tạo ra lớp quản lý Users của chương trình
        AccountManager accountManager = new AccountManager();

        // Đọc file -> load/import danh sách khách hàng vào hệ thống từ file Users.text
        List<User> users = UserFileIO.readUsers();
        for (User user : users) {
            accountManager.createUser(user.getFullName(), user.getPhone(), user.getPassword(), user.getEmail(),
                    user.getBalance());
        }

        // Chạy luồng tự dộng ghi file khi có thay đổi thông tin
        BalanceRefresher balanceRefresher = new BalanceRefresher(accountManager);
        balanceRefresher.start();

        int choice;
        do {
            System.out.println("-------------lỰA CHỌN---------------");
            System.out.println("1. Đăng ký tài khoản ");
            System.out.println("2. Đăng nhập ");
            System.out.println("3. Hiển thị danh sách tài khoản ");
            System.out.println("0. Thoát");
            System.out.println(" Mời bạn nhập lựa chọn: ");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:

                    try {
                        userManage.register(accountManager);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    LoginResponse response = userManage.login(accountManager);
                    if (response.isLoginSuccess()) {
                        do {
                            System.out.println("---lỰA CHỌN---: ");
                            System.out.println("1: Nạp tiền  ");
                            System.out.println("2: Rút tiền  ");
                            System.out.println("3: Chuyển tiền  ");
                            System.out.println("4: Xem số dư tài khoản ");
                            System.out.println("0: Đăng xuất ");
                            choice = Integer.parseInt(scanner.nextLine());
                            switch (choice) {
                                case 1:
                                    double amount;
                                    System.out.println("Nhập số tiền nạp: ");
                                    try {
                                        amount = Double.parseDouble(scanner.nextLine());
                                        accountManager.deposit(response.getPhone(), amount);
                                    } catch (Exception e) {
                                        System.out.println("lỗi: " + e.getMessage());

                                    }
                                    double balance = accountManager.getBalance(response.getPhone());
                                    System.out.printf("Tài khoản: " + response.getPhone() + " " + " Số dư: " + balance + "\n");
                                    break;
                                case 2:
                                    double amountW;
                                    System.out.println("Nhập số tiền rút: ");

                                    try {
                                        amountW = Double.parseDouble(scanner.nextLine());
                                        accountManager.withdraw(response.getPhone(), amountW);
                                    } catch (Exception e) {
                                        System.out.println("Lỗi: " + e.getMessage());

                                    }
                                    double balanceW = accountManager.getBalance(response.getPhone());
                                    System.out.printf("Tài khoản: " + response.getPhone() + " " + "Số dư: " + balanceW + "\n");
                                    break;
                                case 3:
                                    double amountTransfer;
                                    String toPhoneNumber;
                                    System.out.println("Nhập số tài khoản nhận: ");
                                    toPhoneNumber = scanner.nextLine();
                                    System.out.println("Nhập số tiền chuyển: ");
                                    try {
                                        amountTransfer = Double.parseDouble(scanner.nextLine());
                                        accountManager.transfer(response.getPhone(), toPhoneNumber, amountTransfer);
                                    } catch (Exception e) {
                                        System.out.println("Lỗi: " + e.getMessage());

                                    }
                                    double balanceTransfer = accountManager.getBalance(response.getPhone());
                                    System.out.printf("Tài khoản: " + response.getPhone() + " " + " Số dư: " + balanceTransfer + "\n");

                                    break;
                                case 4:
                                    double balance2 = accountManager.getBalance(response.getPhone());
                                    System.out.printf("Tài khoản: " + response.getPhone() + " " + " Số dư: " + balance2 + "\n");
                                    break;
                            }
                        } while (choice != 0);
                    }
                    break;
                case 3:
                    userManage.display(accountManager);
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        } while (true);
    }

}
