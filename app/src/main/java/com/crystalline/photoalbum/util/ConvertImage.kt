package com.crystalline.photoalbum.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class ConvertImage {
    companion object {
        fun convertToBitmap(imageAsString: String) : Bitmap {
            val byteArrayAsDecodedString = Base64.decode(imageAsString, Base64.DEFAULT)
            val bitmamp = BitmapFactory.decodeByteArray(byteArrayAsDecodedString, 0, byteArrayAsDecodedString.size)
            return bitmamp
        }
    }
}