package com.avi.infinitywalls.Adapters

import android.content.Context
import com.google.android.material.carousel.CarouselLayoutManager

class CustomCarouselLayoutManager(context: Context) : CarouselLayoutManager() {

    override fun canScrollVertically(): Boolean {
        // Disable vertical scrolling for the carousel
        return false
    }
}