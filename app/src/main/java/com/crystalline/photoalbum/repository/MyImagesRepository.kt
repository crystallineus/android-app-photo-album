package com.crystalline.photoalbum.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.crystalline.photoalbum.model.MyImages
import com.crystalline.photoalbum.room.MyImagesDao
import com.crystalline.photoalbum.room.MyImagesDatabase

class MyImagesRepository(application: Application) {
    var myImagesDao : MyImagesDao
    var imagesList : LiveData<List<MyImages>>

    init {
        val database = MyImagesDatabase.getDatabaseInstance(application)
        myImagesDao = database.myImagesDao()
        imagesList = myImagesDao.getAll()
    }

    suspend fun insert(myImages: MyImages) {
        myImagesDao.insert(myImages)
    }

    suspend fun update(myImages: MyImages) {
        myImagesDao.update(myImages)
    }

    suspend fun delete(myImages: MyImages) {
        myImagesDao.delete(myImages)
    }

    fun getAll() : LiveData<List<MyImages>> {
        return imagesList
    }

    suspend fun getItemById(id: Int):MyImages {
        return myImagesDao.getItemById(id)
    }
}