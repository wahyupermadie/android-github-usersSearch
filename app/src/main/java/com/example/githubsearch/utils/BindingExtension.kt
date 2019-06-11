package com.example.githubsearch.utils

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class BindingExtension{
    companion object{

        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageUrl(view: CircleImageView, url: String) {
            Glide.with(view.context)
                .asBitmap()
                .centerCrop()
                .load(url)
                .into(view)
        }
    }
}