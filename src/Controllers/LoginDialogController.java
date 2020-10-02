package Controllers;

import Dao.*;
import Models.User;
import Singleton.UserSingleton;

import java.util.Map;

public class LoginDialogController {

    private static LoginDAO loginDAO;
    private static UserDAO userDAO ;

    public LoginDialogController(){
        loginDAO = DAOFactory.getLoginDAO();
        userDAO = DAOFactory.getUserDAO();
    }

    public int loginAdmin(String usernameOrEmail,String password){
        String response = loginDAO.logIn(usernameOrEmail,password);
        if (response!=null){
            User loggedUser = userDAO.getByUsernameOrEmail(usernameOrEmail);
            for (Object curr: loggedUser.getRoles()){
                Map<Long,String> conv = (Map<Long,String>) curr;
                if (conv.containsValue("ROLE_ADMIN")){
                    UserSingleton user = UserSingleton.getInstance();
                    user.setLoggedUser(loggedUser,response);
                    return 1; // login avvenuto con successo
                }
            }
            return 0; //l'utente che cerca di loggare non ha i permessi di admin
        }else {
            return -1; // le credenziali inserite sono errate
        }
    }
}
