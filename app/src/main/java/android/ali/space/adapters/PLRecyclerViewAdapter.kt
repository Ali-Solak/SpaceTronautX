package android.ali.space.adapters

import android.ali.space.R
import android.ali.space.database.ModelsLocal.PastLaunches.PastLaunch
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class PLRecyclerViewAdapter : RecyclerView.Adapter<PLRecyclerViewAdapter.PastLaunchesViewHolder>() {

    inner class PastLaunchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<PastLaunch>() {
        override fun areItemsTheSame(oldItem: PastLaunch, newItem: PastLaunch): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PastLaunch, newItem: PastLaunch): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastLaunchesViewHolder {
        return PastLaunchesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((PastLaunch) -> Unit)? = null

    override fun onBindViewHolder(holder: PastLaunchesViewHolder, position: Int) {

        val pastLaunch = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(pastLaunch?.patch)
                .apply(RequestOptions.placeholderOf(R.drawable.progress_indi)).into(ivArticleImage)
            title.text = pastLaunch.name
            description.text = pastLaunch.date_local


            setOnClickListener {
                onItemClickListener?.let { it(pastLaunch) }
            }
        }
    }

    fun setOnItemClickListener(listener: (PastLaunch) -> Unit) {
        onItemClickListener = listener
    }
}













