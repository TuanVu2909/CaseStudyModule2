package service.banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.User;

public class AccountManager {
    private final Map<String, User> users;


    public AccountManager() {
        users = new HashMap<>();
    }

    public synchronized void createUser(String fullName, String phone, String password, String email, double balance) {
        users.put(phone, new User(fullName, phone, password, email, balance));
    }

    public synchronized void deposit(String phone, double amount) {
        User user = users.get(phone);
        if (user == null) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản");
        }
        user.deposit(amount);
    }

    public synchronized void withdraw(String phone, double amount) {
        User user = users.get(phone);
        if (user == null) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản");
        }
        user.withdraw(amount);
    }

    public synchronized double getBalance(String phone){
        User user = users.get(phone);
        if (user == null) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản");
        }
        return user.getBalance();
    }

    public synchronized List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public boolean transfer(String fromPhone, String destinationPhone, double amount) {
        User userFrom = users.get(fromPhone);
        User userDest = users.get(destinationPhone);

        if (userDest == null) {
            throw new IllegalArgumentException("Số tài khoản nhận không tồn tại");
        }

        if (userFrom.getPhone().equals(destinationPhone)) {
            throw new IllegalArgumentException("Không được chuyển đến chính tài khoản của mình");
        }

        if (userFrom.getBalance() < amount) {
            throw new IllegalArgumentException("Số dư không đủ");
        }

        if (destinationPhone == null) {
            throw new IllegalArgumentException("Tài khoản đích không được để trống");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Số tiền không hợp lệ");
        }

        try {
            userFrom.withdraw(amount);
            userDest.deposit(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
