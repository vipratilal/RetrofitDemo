package com.app.retrofitdemo.database;

import com.app.retrofitdemo.dao.UserDao;
import com.app.retrofitdemo.model.User;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 2)
public abstract class MyDatabase extends RoomDatabase {


    public abstract UserDao userDao();


}
