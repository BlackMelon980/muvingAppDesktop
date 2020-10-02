package Dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LoginDAOimpl implements LoginDAO {

    private static final String signIn = "http://localhost:5000/api/auth/signin";

    @Override
    public String logIn(String usernameOrEmail, String password) {
        String body = new StringBuilder()
                .append("{")
                .append("\"usernameOrEmail\":")
                .append("\"" + usernameOrEmail + "\"")
                .append(",\"password\":")
                .append("\"" + password + "\"")
                .append("}").toString();

        final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(signIn))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = null;

        try{
            response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        } finally {
            if (response.statusCode()==200){
                Gson gson = new Gson();
                JsonObject object = gson.fromJson(response.body(),JsonObject.class);
                return object.get("accessToken").getAsString();
            }else {
                return null;
            }
        }
    }
}
