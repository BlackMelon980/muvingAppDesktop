package Dao;

public class DAOFactory {

    public static UserDAO getUserDAO(){
        return new UserDAOimpl();
    }

    public static LoginDAO getLoginDAO(){
        return new LoginDAOimpl();
    }

    public static ReviewDAO getReviewDAO(){return new ReviewDAOimpl();}
}
