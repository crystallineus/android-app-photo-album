package com.crystalline.photoalbum.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.crystalline.photoalbum.R
import com.crystalline.photoalbum.databinding.ActivityUpdateImageBinding
import com.crystalline.photoalbum.model.MyImages
import com.crystalline.photoalbum.util.ConvertImage
import com.crystalline.photoalbum.viewmodel.MyImagesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateImageActivity : AppCompatActivity() {

    lateinit var updateImageBinding : ActivityUpdateImageBinding
    var id = -1
    lateinit var viewModel: MyImagesViewModel
    var imageAsString = ""
    lateinit var activityResultLaucherForSelectImage : ActivityResultLauncher<Intent>
    lateinit var selectedImage : Bitmap
    var control = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateImageBinding = ActivityUpdateImageBinding.inflate(layoutInflater)
        setContentView(updateImageBinding.root)

        viewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]
        getAndSetData()

        registerActivityForSelectImage()

        updateImageBinding.imageViewUpdateImage.setOnClickListener {
            // access the images
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // startActivityForResult -> Before API 30
            activityResultLaucherForSelectImage.launch(intent)
        }

        updateImageBinding.buttonUpdate.setOnClickListener {
            updateImageBinding.buttonUpdate.text = "Uploading... Please Wait"
            updateImageBinding.buttonUpdate.isEnabled = false

            GlobalScope.launch(Dispatchers.IO) {
                val updateTitle = updateImageBinding.editTextUpdateTitle.text.toString()
                val updateDescription = updateImageBinding.editTextUpdateDescription.text.toString()
                if (control) {
                    val newImageAsString = ConvertImage.convertToString(selectedImage)
                    if (newImageAsString != null) {
                        imageAsString = newImageAsString
                    } else {
                        Toast.makeText(applicationContext, "There is a problem, please select a new image",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                val myUpdatedImage = MyImages(updateTitle, updateDescription, imageAsString)
                myUpdatedImage.imageId = id
                viewModel.update(myUpdatedImage)
                finish()
            }
        }

        updateImageBinding.toolbarUpdateImage.setNavigationOnClickListener {
            finish()
        }
    }

    fun getAndSetData() {
        id = intent.getIntExtra("id", -1)
        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val myImage = viewModel.getItemById(id)

                withContext(Dispatchers.Main) {
                    updateImageBinding.editTextUpdateTitle.setText(myImage.imageTitle)
                    updateImageBinding.editTextUpdateDescription.setText(myImage.imageDescription)
                    imageAsString = myImage.imageAsString
                    val imageAsBitmap = ConvertImage.convertToBitmap(imageAsString)
                    updateImageBinding.imageViewUpdateImage.setImageBitmap(imageAsBitmap)
                }
            }
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

                    updateImageBinding.imageViewUpdateImage.setImageBitmap(selectedImage)
                    control = true
                }
            }
        }
    }
}