package io.github.arshcaria.kerberos.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface ItemDao {
    @Query("SELECT * from $TABLE_NAME_ITEMS")
    fun getAll(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Query("DELETE from $TABLE_NAME_ITEMS")
    fun deleteAll()
}