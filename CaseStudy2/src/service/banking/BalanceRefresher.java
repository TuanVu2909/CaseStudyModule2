package service.banking;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import ioFile.UserFileIO;
import model.User;


public class BalanceRefresher implements Runnable {
    private AccountManager accountManager;

    private ScheduledExecutorService executorService;

    private int userCount = 0;
    private List<User> users;

    public BalanceRefresher(AccountManager accountManager) {
        this.accountManager = accountManager;
        this.userCount = accountManager.getAllUsers().size(); // Lấy số lượng khách hàng trong hệ thống
        this.users = accountManager.getAllUsers(); // Lấy danh sách khách hàng trong hệ thống
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        // 2 Giây chạy ghi file 1 lần, TimeUnit: Giây
        executorService.scheduleAtFixedRate(this, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        try {
            // Lấy danh sách khách hàng mới
            List<User> users = accountManager.getAllUsers();
            // Kiểm tra nếu danh sách khách hàng tăng lên so với danh sách
            // cũ thì mới thực hiện ghi ra file
            if (users.size() > userCount) {
                UserFileIO.writerUsers(users);
                this.userCount = users.size();
                System.out.println("Ghi ra file");
            }

            // Kiểm tra số dư các tài khoản có thay đổi hay không!
            boolean isLoadNewUserList = false;

            // Duyệt danh sách khách hàng mới
            for (User userNew : users){
                // Duyệt danh sách khách hàng cũ
                for(User userOld : this.users) {
                    // Kiểm tra số dư khách hàng có thay đổi hay không?
                    if (userOld.getBalance() != userNew.getBalance()){
                        isLoadNewUserList = true;
                    }
                }
            }

            // Có thay đổi ?
            if (isLoadNewUserList){
                // Ghi danh sách mới ra file
                //System.out.println("Cập nhật số dư!");
                UserFileIO.writerUsers(users);
                this.users = accountManager.getAllUsers();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}