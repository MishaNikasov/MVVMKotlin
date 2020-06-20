package com.my.project.firstkotlin.ui.util

import android.text.util.Linkify
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.my.project.firstkotlin.R
import java.math.RoundingMode
import java.text.DecimalFormat

@BindingAdapter("load_image")
fun bindingImage(imageView : ImageView, imageUrl : String?) {
    imageUrl?.let{
        Glide
            .with(imageView.context)
            .load(imageUrl)
            .centerCrop()
            .dontAnimate()
            .placeholder(R.drawable.recipe_holder)
            .into(imageView)
    }
}

@BindingAdapter("html_text")
fun bindingHtmlText(textView : TextView, text : String?) {
    text?.let{
        textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        Linkify.addLinks(textView, Linkify.WEB_URLS)
    }
}

@BindingAdapter("string_convert")
fun bindingStringText(textView : TextView, any : Any?) {
    any?.let{
        textView.text = any.toString()
    }
}

@BindingAdapter("double_convert")
fun bindingDoubleText(textView : TextView, double : Double?) {
    double?.let{
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        val formatDouble = df.format(double)
        textView.text = formatDouble
    }
}