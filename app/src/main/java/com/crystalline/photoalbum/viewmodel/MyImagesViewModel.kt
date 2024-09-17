package com.crystalline.photoalbum.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.crystalline.photoalbum.model.MyImages
import com.crystalline.photoalbum.repository.MyImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyImagesViewModel(application: Application) : AndroidViewModel(application) {
    var repository : MyImagesRepository
    var imagesList : LiveData<List<MyImages>>

    init {
        repository = MyImagesRepository(application)
        imagesList = repository.getAll()
    }

    fun insert(myImages: MyImages) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(myImages)
    }

    fun update(myImages: MyImages) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(myImages)
    }

    fun delete(myImages: MyImages) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(myImages)
    }

    fun getAll() : LiveData<List<MyImages>> {
        return imagesList
    }
}