package ohtu.services;

import ohtu.domain.User;
import java.util.regex.Pattern;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class AuthenticationService {

    private UserDao userDao;

    
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (checkUserLogin(user, username, password)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkUserLogin(User user, String username, String password) {
        return user.getUsername().equals(username) && user.getPassword().equals(password);
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }
        if (invalid(username, password)) {
            return false;
        }
        userDao.add(new User(username, password));
        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        if (username.length() < 3 || !username.matches("([a-z]*)")) {
            return true;
        }       
        return checkPassword(password);
    }

    private boolean checkPassword(String password) {
        Pattern p = Pattern.compile("^\\S*(?=\\S*[a-zA-Z])(?=\\S*[0-9])\\S*$");
        return password.length() < 8 || !p.matcher(password).matches();
    }

}
