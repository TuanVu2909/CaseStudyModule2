package service;

import model.LoginResponse;
import service.banking.AccountManager;

public interface Manage<E> {
    LoginResponse login(AccountManager accountManager);
    void register(AccountManager accountManager);
    void display(AccountManager accountManager);
}
