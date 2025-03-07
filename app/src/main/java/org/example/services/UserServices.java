package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.User;
import org.example.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserServices {
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<User> usersList;
    private User user;
    private final String USERS_PATH = "app/src/main/java/org/example/db/users.json";

    public UserServices() throws IOException {
        loadUsers();
    }

    public  UserServices(User user) throws IOException {
        this.user=user;
        loadUsers();
    }

    private void loadUsers() throws IOException {
        usersList = objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>() {});
    }

    public Boolean loginUser() {
        Optional<User> foundUser = usersList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }
}
