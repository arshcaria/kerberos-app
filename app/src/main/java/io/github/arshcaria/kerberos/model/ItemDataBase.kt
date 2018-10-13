package io.github.arshcaria.kerberos.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = [Item::class], version = 1)
abstract class ItemDataBase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}