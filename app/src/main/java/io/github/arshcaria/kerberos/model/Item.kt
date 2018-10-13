package io.github.arshcaria.kerberos.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

public const val TABLE_NAME_ITEMS = "items"

@Entity(tableName = TABLE_NAME_ITEMS)
data class Item(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "content") var content: String,
        @ColumnInfo(name = "photo_uri") var photoBlob: String,
        @ColumnInfo(name = "type") var type: Int) {
    constructor() : this(null, "", "", "", 0)

    companion object {
        const val TYPE_UNKNOWN = 0
        const val TYPE_TEXT_NOTE = 1
        const val TYPE_TEXT_PHOTO = 2
    }
}