package tads.ufrn.edu.br.registerusers.repository;

import java.util.ArrayList;
import java.util.List;
import tads.ufrn.edu.br.registerusers.model.User;

public class UserRepository {
     private static final List<User> users = new ArrayList<>();

    public static void save(User user) {
        users.add(user);
    }

    public static List<User> findAll() {
        return users;
    }
}
