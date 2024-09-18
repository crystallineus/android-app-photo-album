package com.crystalline.photoalbum.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.crystalline.photoalbum.R
import com.crystalline.photoalbum.databinding.ActivityMainBinding
import com.crystalline.photoalbum.viewmodel.MyImagesViewModel

class AddImageActivity : AppCompatActivity() {

    lateinit var myImagesViewModel: MyImagesViewModel
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]
        myImagesViewModel.getAll().observe(this, { image->
            // update UI
        })

        mainBinding.floatingActionButton.setOnClickListener {
            // open AddImageActivity

            val intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)
        }
    }
}