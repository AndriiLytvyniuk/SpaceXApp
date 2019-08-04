package alytvyniuk.com.data.repository.imageLoader

import alytvyniuk.com.model.ImageLoader
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso

internal class ImageLoaderImpl : ImageLoader {
    override fun loadImage(from: String, to: ImageView, placeHolder: Drawable?) {
        var creator = Picasso.get().load(from)
        if (placeHolder != null) {
            creator = creator.placeholder(placeHolder)
        }
        creator.into(to)
    }
}