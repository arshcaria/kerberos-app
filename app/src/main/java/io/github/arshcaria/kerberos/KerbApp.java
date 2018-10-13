package io.github.arshcaria.kerberos;

import android.app.Application;
import android.arch.persistence.room.Room;

import io.github.arshcaria.kerberos.model.ItemDataBase;

public class KerbApp extends Application {

    public static ItemDataBase db;

    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(this, ItemDataBase.class, "items.db")
                .allowMainThreadQueries().build();
    }
}
