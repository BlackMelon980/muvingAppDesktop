package DAO;

import Auth.Token;
import Models.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserDAOimpl implements UserDAO{

    private static final String getByUsernameOrEmail = "http://muving-env-2.eba-xepwi2ws.eu-west-2.elasticbeanstalk.com/users/usernameOrEmail/";

    Token token;

    @Override
    public User getByUsernameOrEmail(String usernameOrEmail) {
        User user;
        String ENDPOINT_URL = getByUsernameOrEmail  + "username=" + usernameOrEmail + "&email=" + usernameOrEmail;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestUser = HttpRequest.newBuilder().uri(URI.create(ENDPOINT_URL)).build();

        try{
            HttpResponse<String> responseUser = client.send(requestUser, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            user = gson.fromJson(responseUser.body(), User.class); // Convert json text to User

            return user;
        } catch (IOException | NumberFormatException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void logout() {
        token = null;
    }
}
