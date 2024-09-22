package com.crystalline.photoalbum.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.crystalline.photoalbum.databinding.ActivityAddImageBinding
import com.crystalline.photoalbum.model.MyImages
import com.crystalline.photoalbum.util.ControlPermission
import com.crystalline.photoalbum.util.ConvertImage
import com.crystalline.photoalbum.viewmodel.MyImagesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddImageActivity : AppCompatActivity() {

    lateinit var addImageBinding: ActivityAddImageBinding
    lateinit var activityResultLaucherForSelectImage : ActivityResultLauncher<Intent>
    lateinit var selectedImage: Bitmap
    lateinit var myImagesViewModel : MyImagesViewModel
    var control = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]

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
            if (control) {

                addImageBinding.buttonAdd.text = "Uploading... Please Wait"
                addImageBinding.buttonAdd.isEnabled = false

                GlobalScope.launch(Dispatchers.IO) {
                    val title = addImageBinding.editTextAddTitle.text.toString()
                    val description = addImageBinding.editTextAddDescription.text.toString()
                    val imagesAsString = ConvertImage.convertToString(selectedImage)

                    if (imagesAsString != null) {
                        myImagesViewModel.insert(MyImages(title, description, imagesAsString))
                        control = false
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "There is a problem, please select a new image", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Please select a photo", Toast.LENGTH_SHORT).show()
            }
        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
        }
    }

    fun registerActivityForSelectImage() {
        activityResultLaucherForSelectImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val imageData = result.data

            if (resultCode == RESULT_OK && imageData != null) {
                val imageUri = imageData.data

                imageUri?.let {
                    selectedImage = if (Build.VERSION.SDK_INT >= 28) {
                        val imageSource = ImageDecoder.createSource(this.contentResolver, it)
                        ImageDecoder.decodeBitmap(imageSource)
                    } else {
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    }

                    addImageBinding.imageViewAddImage.setImageBitmap(selectedImage)
                    control = true
                }
            }
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