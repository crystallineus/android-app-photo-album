package com.crystalline.photoalbum.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_images")
data class MyImages(
    var imageTitle: String,
    var imageDescription: String,
    var imageAsString: String
) {
    @PrimaryKey(autoGenerate = true)
    var imageId: Int = 0 // Changed to var for Room to update
}