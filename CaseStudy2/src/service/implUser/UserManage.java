package service.implUser;


import model.LoginResponse;
import model.User;
import service.FormValidate;
import service.Manage;
import service.banking.AccountManager;

import java.util.Scanner;

public class UserManage implements Manage<User> {
    private final Scanner scanner;
    private final FormValidate formValidate = new FormValidate();

    public UserManage(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void register(AccountManager accountManager) {
        System.out.println("Họ tên: ");
        String fullName = scanner.nextLine();
        System.out.println("Số điện thoại: ");
        String phone = scanner.nextLine();
        System.out.println("Mật khẩu: ");
        String password = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();

        if (formValidate.validateFullName(fullName) && formValidate.validatePhone(phone) && formValidate.checkExistUser(phone, accountManager)
                && formValidate.validatePassword(password) && formValidate.validateEmail(email)) {
            accountManager.createUser(fullName, phone, password, email, 0);
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Thất bại! ");
        }
    }

    @Override
    // Trả về đối tượng LoginResponse, lấy số điện thoại
    // để thao tác các chức năng: nạp, rút, chuyển tiền
    public LoginResponse login(AccountManager accountManager) {
        LoginResponse response = new LoginResponse();
        boolean loginSuccess = false;
        System.out.println("Số điện thoại: ");
        String phone = scanner.nextLine();
        System.out.println("Mật khẩu: ");
        String password = scanner.nextLine();
        if (formValidate.validatePhone(phone) && formValidate.validatePassword(password)) {
            for (User user : accountManager.getAllUsers()) {
                if (user.getPhone().equals(phone) && user.getPassword().equals(password)) {
                    System.out.println("Đăng nhập thành công!");
                    System.out.println("Xin chào:  " + user.getFullName());
                    loginSuccess = true;
                }
            }
            if (!loginSuccess) System.out.println("Đăng nhập không thành công!");
        } else {
            System.out.println("Đăng nhập không thành công!");
        }
        response.setLoginSuccess(loginSuccess);
        response.setPhone(phone);
        return response;
    }

    @Override
    public void display(AccountManager accountManager) {
        for (User user : accountManager.getAllUsers()) {
            System.out.println(user);
        }
    }

}
