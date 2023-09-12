import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avi.infinitywalls.Adapters.GridViewModel
import com.avi.infinitywalls.databinding.GridItemLayoutBinding
import com.bumptech.glide.Glide

class GridViewAdapter(private val gridList: List<GridViewModel>) :
    ListAdapter<GridViewModel, GridViewAdapter.ItemViewHolder>(DiffUtilCallback) {

    class ItemViewHolder(private val binding: GridItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: GridViewModel) {
            Glide.with(binding.root)
                .load(model.imageId)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = GridItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = gridList[position]
        holder.bind(model)
    }

    override fun getItemCount() = gridList.size

    object DiffUtilCallback : DiffUtil.ItemCallback<GridViewModel>() {
        override fun areItemsTheSame(oldItem: GridViewModel, newItem: GridViewModel): Boolean {
            return oldItem.imageId == newItem.imageId
        }

        override fun areContentsTheSame(oldItem: GridViewModel, newItem: GridViewModel): Boolean {
            return oldItem == newItem
        }
    }
}
