package com.matoso.mmr.data;

import com.matoso.mmr.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserContext {
    private final static ArrayList<User> _users = new ArrayList<>();

    public static List<User> getAll() {
        return _users;
    }

    public static int count() {
        return _users.size();
    }

    public static void add(User @NotNull ... users) {
        Collections.addAll(_users, users);
    }

    public static @NotNull List<User> get(String key) {
        return getAll().stream()
                .filter(user -> user.getEmail().contains(key) || user.getName().contains(key))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static @Nullable User get(int id) {
        return getAll().stream()
                .filter(user -> user.getId() == id)
                .findFirst().orElse(null);
    }

    public static @Nullable User login(@NotNull String email, @NotNull String password) {
        return getAll().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst().orElse(null);

    }

    public static void update(@NotNull User user) {
        if (remove(user.getId())) {
            _users.add(user);
        }
    }

    public static boolean remove(int id) {
        var user = get(id);
        return user != null && _users.remove(user);
    }
}
