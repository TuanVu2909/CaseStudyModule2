package ioFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserFileIO {

    public static void writerUsers(List<User> users) throws IOException {
        File file = new File("C:\\Users\\Admin\\Desktop\\CaseStudyModule2\\CaseStudy2\\src\\data\\Users.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (User user : users) {
                bufferedWriter.write(user.getId() + ", " + user.getFullName() +
                        ", " + user.getPhone() + ", " + user.getPassword() +
                        ", " + user.getEmail() + ", " + user.getBalance() + "\n");
            }
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
    }

    public static List<User> readUsers() throws IOException {
        File file = new File("C:\\Users\\Admin\\Desktop\\CaseStudyModule2\\CaseStudy2\\src\\data\\Users.txt");
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String data;
            while ((data = bufferedReader.readLine()) != null) {
                String[] strings = data.split(", ");
                users.add(new User(Integer.parseInt(strings[0]), strings[1], strings[2], strings[3], strings[4],
                        Float.parseFloat(strings[5])));
            }

        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        return users;
    }
}
