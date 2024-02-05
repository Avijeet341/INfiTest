package com.avi.infinitywalls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.avi.infinitywalls.Adapters.SliderAdapter
import kotlin.math.abs

class SliderActivity : AppCompatActivity() {
    private lateinit var vpSlider: ViewPager2
    private lateinit var sliderAdapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)
        val selectedPosition = intent.getIntExtra("selectedPosition", 0)
        val allImages = intent.getIntegerArrayListExtra("allImages")

        vpSlider = findViewById(R.id.vpslider)
        vpSlider.offscreenPageLimit = 3
        vpSlider.setPageTransformer(getTransformation())
        sliderAdapter = SliderAdapter()

        sliderAdapter.submitList(allImages)

        vpSlider.adapter = sliderAdapter
        vpSlider.setCurrentItem(selectedPosition, false)
    }

    private fun getTransformation(): CompositePageTransformer {
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(30))
        transform.addTransformer { page, position ->
            val alpha = 0.5f + 0.5f * (1 - abs(position))
            page.alpha = alpha
            page.scaleY = 0.85f + alpha * 0.15f

            val elevation = if (position == 0f) 10f else 0f
            page.translationZ = elevation

            val rotation = -20 * position
            page.rotation = rotation

            val depth = -120 * abs(position)
            page.cameraDistance = 8000f
            page.translationX = depth

            val fadeOut = if (position == 0f) 0f else 0.7f
            page.alpha = 1 - fadeOut * abs(position)
            page.animate().setDuration(300).setInterpolator(DecelerateInterpolator()).start()
        }
        return transform
    }
}
