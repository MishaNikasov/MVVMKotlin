package com.my.project.firstkotlin.common

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class AppEditTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatEditText(context, attrs, defStyle) {

    private fun setFont() {
        typeface = ResourcesCompat.getFont(context, Fonts.FONT_REGULAR)
    }

    init {
        setFont()
    }

}