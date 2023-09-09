package com.avi.infinitywalls.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avi.infinitywalls.databinding.CarouselLayoutBinding
import com.google.android.material.animation.AnimationUtils

class CarouselAdapter(val lis: List<CarouselModel>): ListAdapter<CarouselModel, CarouselAdapter.ItemViewHolder>(DiffUtilCallback) {

    class ItemViewHolder(val binding: CarouselLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("RestrictedApi")
        fun bind(model: CarouselModel) {
            binding.apply {
                carouselImageView.setImageResource(model.imageId)
                carouselTextView.text = model.title
                carouselItemContainer.setOnMaskChangedListener {
                        maskRect->
                    carouselTextView.translationX=maskRect.left
                    carouselTextView.alpha= AnimationUtils.lerp(1F, 0F, 0F, 80F, maskRect.left)
                }
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
        holder.bind(model)
    }

    override fun getItemCount() = lis.size

    object DiffUtilCallback: DiffUtil.ItemCallback<CarouselModel>(){
        override fun areItemsTheSame(oldItem: CarouselModel, newItem: CarouselModel): Boolean {
            return oldItem.imageId==newItem.imageId
        }

        override fun areContentsTheSame(oldItem: CarouselModel, newItem: CarouselModel): Boolean {
            return oldItem==newItem
        }

    }
}