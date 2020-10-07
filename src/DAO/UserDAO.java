package DAO;

import Models.User;

public interface UserDAO {

    User getByUsernameOrEmail(String usernameOrEmail);
    void logout();
}
