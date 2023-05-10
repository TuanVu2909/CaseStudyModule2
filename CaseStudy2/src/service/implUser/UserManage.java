package service.implUser;

import ioFile.IOFile;
import ioFile.UserFileIO;
import model.LoginResponse;
// import model.BankAccount;
import model.User;
import service.FormValidate;
import service.Manage;
import service.banking.AccountManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManage implements Manage<User>, IOFile<User> {
    private final Scanner scanner;
    private final ArrayList<User> userList;
    private final FormValidate formValidate = new FormValidate();
    // private BankAccount threadBankAccount = new BankAccount();

    private final String PART_FILE = "C:\\Users\\Admin\\Desktop\\CaseStudy2abc\\CaseStudy2\\src\\data\\User.txt";

    public UserManage(Scanner scanner) {
        this.scanner = scanner;
        userList = read(PART_FILE);
        checkDefaultIndex();
    }

    private void checkDefaultIndex() {
        if (userList.isEmpty()) {
            User.INDEX = 0;
        } else {
            User.INDEX = userList.get(userList.size() - 1).getId();
        }
    }

    @Override
    public void writer(List<User> e, String path) {
        File file = new File(path);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (User user : e) {
                bufferedWriter.write(user.getId() + ", " + user.getFullName() +
                        ", " + user.getPhone() + ", " + user.getPassword() +
                        ", " + user.getEmail() + ", " + user.getBalance() + "\n");
            }
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }

    }

    @Override
    public ArrayList<User> read(String path) {
        File file = new File(path);
        ArrayList<User> userArrayList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                String[] strings = data.split(", ");
                userArrayList.add(new User(Integer.parseInt(strings[0]), strings[1], strings[2], strings[3], strings[4],
                        Float.parseFloat(strings[5])));
            }

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return userArrayList;
    }

    @Override
    public void register(AccountManager accountManager) {
   
        System.out.println("Enter FullName: ");
        String fullName = scanner.nextLine();
        System.out.println("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter PassWord: ");
        String password = scanner.nextLine();
        System.out.println("Enter Email: ");
        String email = scanner.nextLine();

        if (formValidate.validateFullName(fullName) && formValidate.validatePhone(phone)
                && formValidate.validatePassword(password) && formValidate.validateEmail(email)) {
            // User user = new User(fullName, phone, password, email, 0);
            // userList.add(user);
            accountManager.createUser(fullName, phone, password, email, 0);
            writer(accountManager.getAllUsers(), PART_FILE);
            System.out.println("Register Success");
        } else {
            System.out.println("not! ");
        }
    }

    @Override
    public LoginResponse login() {
        LoginResponse res = new LoginResponse();
        boolean loginSuccess = false;
        read(PART_FILE);
        System.out.println("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter PassWord: ");
        String password = scanner.nextLine();
        if (formValidate.validatePhone(phone) && formValidate.validatePassword(password)) {
            for (User user : userList) {
                if (user.getPhone().equals(phone)) {
                    System.out.println("Hello " + user.getFullName());
                }
            }
            loginSuccess = true;
        } else {
            System.out.println("Login Fail ");
        }

        res.setLoginSuccess(loginSuccess);
        res.setPhone(phone);
        return res;
    }

    @Override
    public void display() {
        for (User user : userList) {
            System.out.println(user);
        }
    }
}
