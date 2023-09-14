import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avi.infinitywalls.Adapters.CategoriesRecyclerViewModel
import com.avi.infinitywalls.R
import com.avi.infinitywalls.databinding.CategoriesItemsLayoutBinding
import com.bumptech.glide.Glide

class CategoriesRecyclerViewAdapter :
    ListAdapter<CategoriesRecyclerViewModel, CategoriesRecyclerViewAdapter.ItemViewHolder>(
        DiffUtilCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoriesItemsLayoutBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model)
    }

    class ItemViewHolder(private val binding: CategoriesItemsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CategoriesRecyclerViewModel) {
            binding.textViewCate.text = model.title
            Glide.with(binding.root)
                .load(model.imageId)
                .placeholder(R.drawable.fire_blue) // Replace with your placeholder image
                .error(R.drawable.fire) // Replace with your error image
                .into(binding.ImageView)// Correct the ImageView ID to match your XML
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<CategoriesRecyclerViewModel>() {
        override fun areItemsTheSame(
            oldItem: CategoriesRecyclerViewModel,
            newItem: CategoriesRecyclerViewModel
        ): Boolean {
            return oldItem.title == newItem.title // Use a unique identifier for your items
        }

        override fun areContentsTheSame(
            oldItem: CategoriesRecyclerViewModel,
            newItem: CategoriesRecyclerViewModel
        ): Boolean {
            return oldItem == newItem // Compare the content of your items here
        }
    }
}
