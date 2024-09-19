package com.crystalline.photoalbum.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.crystalline.photoalbum.R
import com.crystalline.photoalbum.databinding.ActivityUpdateImageBinding

class UpdateImageActivity : AppCompatActivity() {

    lateinit var updateImageBinding : ActivityUpdateImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateImageBinding = ActivityUpdateImageBinding.inflate(layoutInflater)
        setContentView(updateImageBinding.root)

        updateImageBinding.imageViewUpdateImage.setOnClickListener {

        }

        updateImageBinding.buttonUpdate.setOnClickListener {

        }

        updateImageBinding.toolbarUpdateImage.setNavigationOnClickListener {
            finish()
        }
    }
}