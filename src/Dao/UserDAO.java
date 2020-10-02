package Dao;

import Models.User;

public interface UserDAO {

    User getByUsernameOrEmail(String usernameOrEmail);
}
