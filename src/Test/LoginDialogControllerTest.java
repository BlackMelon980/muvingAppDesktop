package Test;

import Controllers.LoginDialogController;
import Models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


public class LoginDialogControllerTest {

    LoginDialogController loginDialogController;
    @Before
    public void setUp(){
        loginDialogController = new LoginDialogController();
        /*String[] roles = {"ROLE_ADMIN"};
        String[] userRoles = {"ROLE_USER"};
        User admin = new User("nome","cognome","admin","password",roles);
        User user = new User("nome","cognome","user","password",userRoles); */
    }

    //black box test

    @Test
    public void loginSenzaPermessoDiAdmin(){
        Assert.assertEquals(loginDialogController.loginAdmin("rihgen_","genny"), 0);
    }

    @Test
    public void loginConCredenialiErrate(){
        Assert.assertEquals(loginDialogController.loginAdmin("admin","password"),-1);
    }

    @Test
    public void loginEffettuatoConSuccesso(){
        Assert.assertEquals(loginDialogController.loginAdmin("admin","admin"),1);
    }

    //white box test




}
