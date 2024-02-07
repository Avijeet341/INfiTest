package com.avi.infinitywalls.Adapters

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avi.infinitywalls.R
import com.bumptech.glide.Glide

class SliderAdapter : ListAdapter<Int, SliderAdapter.ViewHolder>(DiffCallback()) {
    private var listener: OnItemClickListener? = null
    private var likedStates = mutableListOf<Boolean>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivWallpaper: ImageView = itemView.findViewById(R.id.wallpaper)

        @SuppressLint("ClickableViewAccessibility")
        fun onBind(wallpaper: Int) {
            Glide.with(ivWallpaper)
                .load(wallpaper)
                .into(ivWallpaper)

            val doubleTapListener = object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    listener?.onDoubleTap(adapterPosition)
                    return true
                }
            }

            val gestureDetector = GestureDetector(itemView.context, doubleTapListener)
            ivWallpaper.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                true
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    }

    interface OnItemClickListener {
        fun onDoubleTap(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_slider, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    override fun submitList(list: MutableList<Int>?) {
        super.submitList(list)
        // Initialize likedStates with false for each item
        likedStates.clear()
        list?.let {
            for (i in it.indices) {
                likedStates.add(false)
            }
        }
    }

    fun updateLikedState(position: Int, isLiked: Boolean) {
        likedStates[position] = isLiked
    }

    fun isLiked(position: Int): Boolean {
        return likedStates.getOrNull(position) ?: false
    }

}
