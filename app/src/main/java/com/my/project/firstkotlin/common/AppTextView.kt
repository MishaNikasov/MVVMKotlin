package com.my.project.firstkotlin.common

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.my.project.firstkotlin.R

class AppTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView (context, attrs, defStyle) {

    companion object {
        private const val FONT_REGULAR = 0
        private const val FONT_BOLD = 1
    }

    private fun setFont(fontType : Int) {

        var typeface : Typeface? = null

        when (fontType) {
            FONT_REGULAR -> typeface = ResourcesCompat.getFont(context, Fonts.FONT_REGULAR)
            FONT_BOLD -> typeface = ResourcesCompat.getFont(context, Fonts.FONT_BOLD)
        }

        setTypeface(typeface)
    }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.AppTextView, 0, 0).apply {
            try {
                val textType = getInteger(R.styleable.AppTextView_textType, 0)
                setFont(textType)
            } finally {
                recycle()
            }
        }
    }

    fun setType(type : Int) {
        setFont(type)
    }

}