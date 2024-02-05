package com.avi.infinitywalls.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avi.infinitywalls.R
import com.avi.infinitywalls.SliderActivity
import com.avi.infinitywalls.databinding.CarouselLayoutBinding
import com.bumptech.glide.Glide
import com.google.android.material.animation.AnimationUtils

class CarouselAdapter(val lis: List<CarouselModel>) : ListAdapter<CarouselModel, CarouselAdapter.ItemViewHolder>(DiffUtilCallback) {

    class ItemViewHolder(val binding: CarouselLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("RestrictedApi")
        fun bind(model: CarouselModel, context: Context, lis: List<CarouselModel>) {
            binding.apply {
                carouselTextView.text = model.title
                carouselItemContainer.setOnMaskChangedListener { maskRect ->
                    carouselTextView.translationX = maskRect.left
                    carouselTextView.alpha = AnimationUtils.lerp(1F, 0F, 0F, 80F, maskRect.left)
                }

                // Load the image using Glide
                Glide.with(context)
                    .load(model.imageId) // Assuming imageId is the resource ID of the image
                    .placeholder(R.drawable.fire_blue) // You can set a placeholder image
                    .error(R.drawable.fire_blue) // You can set an error image
                    .into(carouselImageView)
            }

            // Set click listener to launch SliderActivity
            itemView.setOnClickListener {
                val intent = Intent(context, SliderActivity::class.java)
                intent.putExtra("selectedPosition", adapterPosition)
                intent.putIntegerArrayListExtra("allImages", lis.map { it.imageId } as ArrayList<Int>)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            CarouselLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = lis[position]
        holder.bind(model, holder.itemView.context, lis)
    }

    override fun getItemCount() = lis.size

    object DiffUtilCallback : DiffUtil.ItemCallback<CarouselModel>() {
        override fun areItemsTheSame(oldItem: CarouselModel, newItem: CarouselModel): Boolean {
            return oldItem.imageId == newItem.imageId
        }

        override fun areContentsTheSame(oldItem: CarouselModel, newItem: CarouselModel): Boolean {
            return oldItem == newItem
        }
    }
}
