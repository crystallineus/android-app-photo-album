package com.crystalline.photoalbum.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.crystalline.photoalbum.databinding.ActivityAddImageBinding
import com.crystalline.photoalbum.util.ControlPermission

class AddImageActivity : AppCompatActivity() {

    lateinit var addImageBinding: ActivityAddImageBinding
    lateinit var activityResultLaucherForSelectImage : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        registerActivityForSelectImage()

        addImageBinding.imageViewAddImage.setOnClickListener {
            if (ControlPermission.checkPermission(this)) {
                // access the images
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // startActivityForResult -> Before API 30
                activityResultLaucherForSelectImage.launch(intent)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        1
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                }
            }
        }

        addImageBinding.buttonAdd.setOnClickListener {

        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
        }
    }

    fun registerActivityForSelectImage() {
        activityResultLaucherForSelectImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // result of the intent
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // access the images
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // startActivityForResult -> Before API 30
            activityResultLaucherForSelectImage.launch(intent)
        }
    }
}