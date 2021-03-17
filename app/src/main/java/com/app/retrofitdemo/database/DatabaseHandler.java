package com.app.retrofitdemo.database;

import com.app.retrofitdemo.model.User;

public class DatabaseHandler {

    private static final String TAG = DatabaseHandler.class.getName();

    public static User insertUser(final MyDatabase db, User user) {
        db.userDao().insert(user);
        return user;
    }

    public static User updateUser(final MyDatabase db, User user) {
        db.userDao().update(user);
        return user;
    }

}
