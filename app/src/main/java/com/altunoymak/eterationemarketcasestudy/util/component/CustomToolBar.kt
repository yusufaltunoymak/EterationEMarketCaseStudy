package com.altunoymak.eterationemarketcasestudy.util.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.altunoymak.eterationemarketcasestudy.R
import com.altunoymak.eterationemarketcasestudy.databinding.CustomToolbarBinding
import com.altunoymak.eterationemarketcasestudy.util.clickWithDebounce

class CustomToolBar(context: Context, attrs: AttributeSet) :
    ConstraintLayout(
        context,
        attrs
    ) {
    private var binding: CustomToolbarBinding

    fun navigationIconSetOnClickListener(function: (view: View) -> Unit) {
        if (binding.navigationIcon.visibility == View.VISIBLE) {
            navigationIconSetOnClickListener = function
        }
    }

    fun setToolbarText(text : String){
        binding.toolbarText.text = text
    }

    private var navigationIconSetOnClickListener: (view: View) -> Unit = {}

    init {
        inflate(context, R.layout.custom_toolbar, this)
        binding = CustomToolbarBinding.bind(this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar)
        val navigationIcon = attributes.getDrawable(R.styleable.CustomToolBar_navigation_icon)
        val navigationIconVisibility =
            attributes.getBoolean(R.styleable.CustomToolBar_navigation_icon_visibility, true)
        val textColor = attributes.getColor(
            R.styleable.CustomToolBar_toolBarTextColor,
            context.getColor(R.color.black)
        )

        val backgroundColor = attributes.getColor(
            R.styleable.CustomToolBar_toolBarBackgroundColor,
            Color.WHITE
        )
        setBackgroundColor(backgroundColor)
        binding.navigationIcon.apply {
            setImageDrawable(navigationIcon)
            visibility = if (navigationIconVisibility) View.VISIBLE else View.GONE
            if (visibility == View.VISIBLE) {
                clickWithDebounce {
                    navigationIconSetOnClickListener.invoke(it)
                }
            }
        }
        binding.toolbarText.apply {
            text = attributes.getString(R.styleable.CustomToolBar_toolBarText)
            setTextColor(textColor)
        }
        attributes.recycle()
    }
}