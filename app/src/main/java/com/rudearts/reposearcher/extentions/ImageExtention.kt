package com.rudearts.reposearcher.extentions

import android.content.res.Resources
import android.support.v4.content.res.ResourcesCompat
import android.widget.ImageView
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso

fun ImageView.loadUrlThumb(size:Int, placeHolderId:Int, url: String) {
    val placeHolder = getImageByResource(resources, placeHolderId)
    Picasso.with(context)
            .load(url)
            .resize(size, size)
            .placeholder(placeHolder)
            .error(placeHolder)
            .transform(thumbCircleTransform())
            .centerCrop()
            .into(this)
}

private fun getImageByResource(resources:Resources, placeHolderId: Int) =
        ResourcesCompat.getDrawable(resources, placeHolderId, null)

private fun thumbCircleTransform() = RoundedTransformationBuilder()
        .oval(true)
        .build()


