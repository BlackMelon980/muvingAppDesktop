package Singleton;

import Models.User;

public class UserSingleton {

    private static UserSingleton instance = null;

    User loggedUser;

    private UserSingleton(){}

    public static UserSingleton getInstance(){
        if(instance==null){
            synchronized (UserSingleton.class){
                if(instance==null){
                    instance = new UserSingleton();
                }
            }
        }
        return instance;
    }

    public User getLoggedUser(){
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser,String token){
        this.loggedUser = loggedUser;
        this.loggedUser.setToken(token);
    }

}
