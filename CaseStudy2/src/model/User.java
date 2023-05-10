package model;

public class User {
    public static int INDEX;
    private int id;
    private String fullName;
    private String phone;
    private String password;
    private String email;
    private double balance;

    public User(String fullName, String phone, String password, String email, double balance) {
        this.id = ++INDEX;
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.balance = balance;

    }

    public User(int id, String fullName, String phone, String password, String email,
            float balance) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.balance = balance;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return id + ", " + fullName + ", " + phone + ", " + password + ", " + email + ", " + balance;
    }
    public void display(){

    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
    }

}
