package demo1.service;

import demo1.dao.UserDao;
import demo1.model.User;


public class UserService {
    private UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public boolean checkUserPresence(User user) throws Exception {
        User u = dao.getUserByUsername(user.getUsername());
        return u != null;
    }
}
