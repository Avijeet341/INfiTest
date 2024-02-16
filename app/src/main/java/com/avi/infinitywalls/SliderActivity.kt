package com.avi.infinitywalls


import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.avi.infinitywalls.Adapters.SliderAdapter
import com.avi.infinitywalls.databinding.ActivitySliderBinding
import java.io.IOException
import kotlin.math.abs

class SliderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySliderBinding
    private lateinit var vpSlider: ViewPager2
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var imageNameTextView: TextView
    private lateinit var zoomInAnim: Animation
    private lateinit var zoomOutAnim: Animation
    private lateinit var layout: View
    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_layout)
        layout = layoutInflater.inflate(R.layout.custom_toast, constraintLayout, false)
        binding.buttonDownload.setOnClickListener {
            // Handle click event here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                createWriteRequest()
            } else {
                downloadImage()
            }
        }
        binding.buttonTheme.setOnClickListener {
            //dialog message
            //apply
            //Set Home Screen
            //Set Lock Screen
            //set both
            showApplyWallpaperDialog()
        }
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
            binding.heart.setImageResource(
                if (isLiked) R.drawable.baseline_favorite_24
                else R.drawable.baseline_favorite_border_24
            )
            // Update liked state of the current image
            sliderAdapter.updateLikedState(vpSlider.currentItem, isLiked)
        }

        // Add listener to handle double tap events from adapter
        sliderAdapter.setOnItemClickListener(
            object : SliderAdapter.OnItemClickListener {
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
            }
        )

        // Add listener to handle page change events
        vpSlider.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
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
            }
        )
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
                val bounceScale =
                    if (absPosition > 1) 0.85f else (0.85f + (1 - absPosition) * 0.15f)
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
    }

    private fun downloadImage() {
        val currentItem = sliderAdapter.currentList[vpSlider.currentItem]
        val bitmap = getBitmapFromDrawable(ContextCompat.getDrawable(this, currentItem))
        saveImageToGallery(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createWriteRequest() {
        val currentItem = sliderAdapter.currentList[vpSlider.currentItem]
        val bitmap = getBitmapFromDrawable(ContextCompat.getDrawable(this, currentItem))

        val resolver = contentResolver
        val contentValues =
            ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

        val imageCollection =
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val imageUri = resolver.insert(imageCollection, contentValues)

        imageUri?.let {
            try {
                resolver.openOutputStream(imageUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
                layout.findViewById<TextView>(R.id.tv_text).setText(R.string.success_download)
                Toast(this)
                    .apply {
                        duration = Toast.LENGTH_LONG
                        setGravity(Gravity.CENTER, 0, 0)
                        view = layout
                    }
                    .show()
            } catch (e: IOException) {
                Toast.makeText(this, "Failed to save image to gallery", Toast.LENGTH_SHORT).show()
            }
        }
            ?: run {
                Toast.makeText(this, "Failed to save image to gallery", Toast.LENGTH_SHORT).show()
            }
    }
    private fun showApplyWallpaperDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_apply_wallpaper, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Apply Wallpaper")
            .create()

        dialogView.findViewById<View>(R.id.button_apply_home_screen).setOnClickListener {
            applyWallpaper(WallpaperType.HOME_SCREEN)
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.button_apply_lock_screen).setOnClickListener {
            applyWallpaper(WallpaperType.LOCK_SCREEN)
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.button_apply_both).setOnClickListener {
            applyWallpaper(WallpaperType.BOTH)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun applyWallpaper(wallpaperType: WallpaperType) {
        val currentItemResourceId = sliderAdapter.currentList[vpSlider.currentItem]
        val drawable = ContextCompat.getDrawable(this, currentItemResourceId)
        val bitmap = getBitmapFromDrawable(drawable)

        try {
            val wallpaperManager = WallpaperManager.getInstance(this)
            val croppedBitmap = centerCropBitmap(bitmap)
            when (wallpaperType) {
                WallpaperType.HOME_SCREEN -> {
                    wallpaperManager.setBitmap(croppedBitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                    showToast("Wallpaper applied to Home Screen")
                }
                WallpaperType.LOCK_SCREEN -> {
                    wallpaperManager.setBitmap(croppedBitmap, null, true, WallpaperManager.FLAG_LOCK)
                    showToast("Wallpaper applied to Lock Screen")
                }
                WallpaperType.BOTH -> {
                    wallpaperManager.setBitmap(croppedBitmap)
                    showToast("Wallpaper applied to both Home and Lock Screen")
                }
            }
        } catch (e: IOException) {
            showToast("Failed to apply wallpaper")
        }
    }

    private fun centerCropBitmap(sourceBitmap: Bitmap): Bitmap {
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val sourceWidth = sourceBitmap.width
        val sourceHeight = sourceBitmap.height

        val cropWidth = minOf(screenWidth, sourceWidth)
        val cropHeight = minOf(screenHeight, sourceHeight)

        val startX = maxOf(0, (sourceWidth - cropWidth) / 2)
        val startY = maxOf(0, (sourceHeight - cropHeight) / 2)

        return Bitmap.createBitmap(
            sourceBitmap,
            startX,
            startY,
            cropWidth,
            cropHeight
        )
    }
    enum class WallpaperType {
        HOME_SCREEN,
        LOCK_SCREEN,
        BOTH
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap {
        drawable ?: return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        if (drawable is BitmapDrawable) {
            // If the drawable is a BitmapDrawable, extract the bitmap
            return drawable.bitmap
        }

        // Otherwise, create a new bitmap with the original dimensions
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val contentValues =
            ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let {
            try {
                resolver.openOutputStream(imageUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
                Toast.makeText(this, "Image saved to gallery!", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Failed to save image to gallery", Toast.LENGTH_SHORT).show()
            }
        }
            ?: run {
                Toast.makeText(this, "Failed to save image to gallery", Toast.LENGTH_SHORT).show()
            }
    }


}