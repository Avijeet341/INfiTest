package com.avi.infinitywalls

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.avi.infinitywalls.Adapters.SliderAdapter
import com.avi.infinitywalls.databinding.ActivitySliderBinding
import kotlin.math.abs

class SliderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySliderBinding
    private lateinit var vpSlider: ViewPager2
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var imageNameTextView: TextView
    private lateinit var zoomInAnim: Animation
    private lateinit var zoomOutAnim: Animation
    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        zoomInAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        zoomOutAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_out)
        val selectedPosition = intent.getIntExtra("selectedPosition", 0)
        val allImages = intent.getIntegerArrayListExtra("allImages")
        val allImageNames = intent.getStringArrayListExtra("allImageNames")
        imageNameTextView = binding.ImageName

        imageNameTextView.text = allImageNames?.get(selectedPosition) ?: "Hello"
        vpSlider = binding.vpslider
        vpSlider.offscreenPageLimit = 3
        vpSlider.setPageTransformer(getTransformation())
        sliderAdapter = SliderAdapter()

        sliderAdapter.submitList(allImages)

        vpSlider.adapter = sliderAdapter
        vpSlider.setCurrentItem(selectedPosition, false)


        binding.heart.setOnClickListener {
            // Toggle like/dislike
            isLiked = !isLiked
            binding.heart.setImageResource(if (isLiked) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
            // Update liked state of the current image
            sliderAdapter.updateLikedState(vpSlider.currentItem, isLiked)
        }

        // Add listener to handle double tap events from adapter
        sliderAdapter.setOnItemClickListener(object : SliderAdapter.OnItemClickListener {
            override fun onDoubleTap(position: Int) {
                // Handle double tap event
                // Update heart and insideHeart views accordingly
                // You can use vpSlider.currentItem to get the current item index
                // Only like the image that is double-tapped
                binding.heart.setImageResource(R.drawable.baseline_favorite_24)
                binding.heart.startAnimation(zoomInAnim)
                binding.insideHeart.startAnimation(zoomInAnim)
                binding.insideHeart.startAnimation(zoomOutAnim)
                isLiked = true
                // Update liked state of the current image
                sliderAdapter.updateLikedState(vpSlider.currentItem, isLiked)
            }
        })

        // Add listener to handle page change events
        vpSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Update the liked state of the current image
                isLiked = sliderAdapter.isLiked(position)
                if (isLiked) {
                    binding.heart.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.heart.setImageResource(R.drawable.baseline_favorite_border_24)
                }
                // Update the ImageName textView value
                imageNameTextView.text = allImageNames?.get(position) ?: ""
            }
        })
    }

    private fun getTransformation(): CompositePageTransformer {
        return CompositePageTransformer().apply {
            // Add margin to each page
            addTransformer(MarginPageTransformer(30))

            // Apply a custom transformation
            addTransformer { page, position ->
                // Calculate alpha value for transparency
                val alpha = 0.5f + 0.5f * (1 - abs(position))

                // Set transparency and scale based on position
                page.alpha = alpha
                page.scaleY = 0.85f + alpha * 0.15f

                // Add elevation for a 3D effect
                val elevation = if (position == 0f) 5f else 0f
                page.translationZ = elevation

                // Apply rotation
                val rotation = -20 * position
                page.rotation = rotation

                // Create a 3D depth effect
                val depth = -120 * abs(position)
                page.cameraDistance = 8000f
                page.translationX = depth

                // Apply fading effect with bounce animation
                val fadeOut = if (position == 0f) 0f else 0.7f
                val fadeAlpha = 1 - fadeOut * abs(position)
                page.alpha = fadeAlpha

                // Apply bounce effect
                val absPosition = abs(position)
                val bounceScale = if (absPosition > 1) 0.85f else (0.85f + (1 - absPosition) * 0.15f)
                page.scaleX = bounceScale
                page.scaleY = bounceScale

                // Adjust saturation
                if (page is ImageView) {
                    val saturation = 1 - 0.5f * abs(position)
                    val colorMatrix = ColorMatrix().apply { setSaturation(saturation) }
                    val filter = ColorMatrixColorFilter(colorMatrix)
                    page.colorFilter = filter
                }
            }
        }
    }}
