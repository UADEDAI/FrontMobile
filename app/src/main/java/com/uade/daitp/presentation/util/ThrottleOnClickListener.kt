package com.uade.daitp.presentation.util

import android.os.SystemClock
import android.view.View
import android.view.View.OnClickListener

fun View.setOnClickListenerWithThrottle(periodInMillis: Long = 1000L, action: (View) -> Unit) {
    setOnClickListener(ThrottleOnClickListener(periodInMillis, action))
}

class ThrottleOnClickListener(
    private val periodInMillis: Long = 1000L,
    private val action: (View) -> Unit
) : OnClickListener {
    private var lastClickMillis = 0L

    override fun onClick(v: View) {
        SystemClock.elapsedRealtime().let { currentTimeMillis ->
            if (currentTimeMillis - lastClickMillis < periodInMillis) return
            lastClickMillis = currentTimeMillis
        }

        action(v)
    }
}

fun OnClickListener.throttle(): ThrottleOnClickListener {
    return ThrottleOnClickListener { onClick(it) }
}