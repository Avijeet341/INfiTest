package com.avi.infinitywalls

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
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
         imageNameTextView=binding.ImageName
        vpSlider = binding.vpslider
        vpSlider.offscreenPageLimit = 3
        vpSlider.setPageTransformer(getTransformation())
        sliderAdapter = SliderAdapter()

        sliderAdapter.submitList(allImages)

        vpSlider.adapter = sliderAdapter
        vpSlider.setCurrentItem(selectedPosition, false)


        binding.heart.setOnClickListener {
            // Toggle like/dislike
            isLiked = if (isLiked) {
                // Dislike
                binding.heart.setImageResource(R.drawable.baseline_favorite_border_24)
                false
            } else {
                // Like
                binding.heart.setImageResource(R.drawable.baseline_favorite_24)
                true
            }
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