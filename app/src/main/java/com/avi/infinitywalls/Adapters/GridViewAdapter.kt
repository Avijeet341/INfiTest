import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avi.infinitywalls.Adapters.GridViewModel
import com.avi.infinitywalls.R

class GridViewAdapter(private val gridList: List<GridViewModel>): ListAdapter<GridViewModel, GridViewAdapter.ItemViewHolder>(DiffUtilCallback) {

    class ItemViewHolder(val binding: View) :
        RecyclerView.ViewHolder(binding) {
        fun bind(model: GridViewModel) {
            binding.apply {
                val imageView: ImageView = binding.findViewById(R.id.image_view)
                imageView.setImageResource(model.imageId)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = gridList[position]
        holder.bind(model)
    }

    override fun getItemCount() = gridList.size

    object DiffUtilCallback: androidx.recyclerview.widget.DiffUtil.ItemCallback<GridViewModel>(){
        override fun areItemsTheSame(oldItem: GridViewModel, newItem: GridViewModel): Boolean {
            return oldItem.imageId == newItem.imageId
        }

        override fun areContentsTheSame(oldItem: GridViewModel, newItem: GridViewModel): Boolean {
            return oldItem == newItem
        }
    }
}