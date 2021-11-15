package com.alex3645.feature_conference_builder.customUI

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class LockableScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ScrollView(context, attrs) {

    // true if we can scroll (not locked)
    // false if we cannot scroll (locked)
    private var scrollable = true

    fun setScrollingEnabled(enabled: Boolean) {
        scrollable = enabled
    }

   override fun onTouchEvent(ev: MotionEvent) : Boolean{
        return when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                scrollable && super.onTouchEvent(ev)
            }
            else -> {
                super.onTouchEvent(ev)
                performClick()
            }
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent) : Boolean{
        // Don't do anything with intercepted touch events if
        // we are not scrollable
        return scrollable && super.onInterceptTouchEvent(ev)
    }

}