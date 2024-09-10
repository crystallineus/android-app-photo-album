package com.crystalline.photoalbum.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_images")
class MyImages (
    val imageTitle : String,
    val imageDescription : String,
    var imageAsString : String
) {
    @PrimaryKey(autoGenerate = true)
    val imageId = 0
}