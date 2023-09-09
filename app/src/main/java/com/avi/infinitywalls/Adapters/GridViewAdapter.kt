package com.avi.infinitywalls.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.avi.infinitywalls.R

class GridViewAdapter(private val gridList: ArrayList<GridViewModel>, private val context: Context) :
    RecyclerView.Adapter<GridViewAdapter.GridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_layout, parent, false)
        return GridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gridList.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val gridItem = gridList[position]
        holder.bind(gridItem)
    }

    inner class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.image_view)

        fun bind(gridItem: GridViewModel) {
            imageView.setImageResource(gridItem.imageId)
        }
    }
}
