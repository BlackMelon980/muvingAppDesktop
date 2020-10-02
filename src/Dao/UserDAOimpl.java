package Dao;

import Models.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserDAOimpl implements UserDAO{

    private static final String getByUsernameOrEmail = "http://localhost:5000/users/usernameOrEmail/";

    @Override
    public User getByUsernameOrEmail(String usernameOrEmail) {
        User user = new User();

        String ENDPOINT_URL = getByUsernameOrEmail  + "username=" + usernameOrEmail + "&email=" + usernameOrEmail;

        HttpClient client = HttpClient.newHttpClient(); // SHUTDOWN?
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
}
