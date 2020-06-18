package demo1.dao;

import demo1.model.User;

import java.util.List;

public interface UserDao {
    User getUserByUsername(String username) throws Exception;
    List<User> findAllUsers();
}
