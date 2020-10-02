package Dao;

import Models.User;

public interface UserDAO {

    public User getByUsernameOrEmail(String usernameOrEmail);
}
