package alytvyniuk.com.model

import android.graphics.drawable.Drawable
import android.widget.ImageView

interface ImageLoader {

    fun loadImage(from: String, to: ImageView, placeHolder: Drawable? = null)
}