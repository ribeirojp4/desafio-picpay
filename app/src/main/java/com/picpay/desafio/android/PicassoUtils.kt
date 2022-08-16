package com.picpay.desafio.android

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadFromPicasso(
    imageUrl: String,
    @DrawableRes errorPlaceholder: Int,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    Picasso.get()
        .load(imageUrl)
        .error(errorPlaceholder)
        .into(this, object : Callback {
            override fun onSuccess() {
                onSuccess()
            }

            override fun onError(e: java.lang.Exception?) {
                onError()
            }
        })
}
