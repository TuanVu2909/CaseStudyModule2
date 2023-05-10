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
            throw new IllegalArgumentException("Account not found");
        }
        user.deposit(amount);
    }

    public synchronized void withdraw(String phone, double amount) {
        User user = users.get(phone);
        if (user == null) {
            throw new IllegalArgumentException("Account not found");
        }
        user.withdraw(amount);
    }

    public synchronized double getBalance(String phone){
        User user = users.get(phone);
        if (user == null) {
            throw new IllegalArgumentException("Account not found");
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
            throw new IllegalArgumentException("Number Account does not exist");
        }

        if (userFrom.getPhone().equals(destinationPhone)) {
            throw new IllegalArgumentException("Can't transfer to my own account number");
        }

        if (userFrom.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        if (destinationPhone == null) {
            throw new IllegalArgumentException("The target account cannot be empty");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
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
