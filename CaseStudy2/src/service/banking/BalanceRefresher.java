package service.banking;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ioFile.IOFile;
import ioFile.UserFileIO;
import model.User;
import service.implUser.UserManage;

public class BalanceRefresher implements Runnable {
    private AccountManager accountManager;

    private ScheduledExecutorService executorService;

    public BalanceRefresher(AccountManager accountManager) {
        this.accountManager = accountManager;
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        executorService.scheduleAtFixedRate(this, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        try {
            List<User> users = accountManager.getAllUsers();
            UserFileIO.saveUsers(users);
            // System.out.println("Accounts saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}