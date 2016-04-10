/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ohtu.domain.User;

/**
 *
 * @author Simo
 */
public class FileUserDao implements UserDao {

    private Scanner reader;
    private String fileName;
    ArrayList<User> users;

    public FileUserDao(String file) {
        try {
            this.fileName = file;
            reader = new Scanner(new File(file));
            readFile();
        } catch (FileNotFoundException ex) {
            this.users = new ArrayList<User>();
        }
    }

    private void readFile() {
        this.users = new ArrayList<User>();
        while (reader.hasNext()) {
            users.add(new User(reader.next(), reader.next()));
        }
    }

    private void addUserToFile(User user) throws IOException {
        BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));
        fw.write(user.getUsername() + " " + user.getPassword());
        fw.newLine();
        fw.flush();

    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        users.add(user);
        try {
            addUserToFile(user);
        } catch (IOException ex) {
            System.out.println("ERROR: USER FILE");
        }
    }

}
