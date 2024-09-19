package com.crystalline.photoalbum.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.crystalline.photoalbum.R
import com.crystalline.photoalbum.databinding.ActivityMainBinding
import com.crystalline.photoalbum.viewmodel.MyImagesViewModel

class MainActivity : AppCompatActivity() {
    lateinit var myImagesViewModel: MyImagesViewModel
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]
        myImagesViewModel.getAll().observe(this, {
            // update UI
        })

        mainBinding.floatingActionButton.setOnClickListener {
            // open AddImageActivity
            val intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)
        }
    }
}