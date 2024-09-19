package com.crystalline.photoalbum.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.crystalline.photoalbum.R
import com.crystalline.photoalbum.databinding.ActivityAddImageBinding
import com.crystalline.photoalbum.databinding.ActivityMainBinding
import com.crystalline.photoalbum.viewmodel.MyImagesViewModel

class AddImageActivity : AppCompatActivity() {

    lateinit var addImageBinding: ActivityAddImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        addImageBinding.imageViewAddImage.setOnClickListener {

        }

        addImageBinding.buttonAdd.setOnClickListener {

        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
        }
    }
}