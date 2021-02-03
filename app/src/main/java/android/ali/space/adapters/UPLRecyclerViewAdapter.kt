package android.ali.space.adapters

import android.ali.space.R
import android.ali.space.database.ModelsLocal.UpcomingLaunches.UpcomingLaunch
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class UPLRecyclerViewAdapter : RecyclerView.Adapter<UPLRecyclerViewAdapter.UpcomingLaunchesViewHolder>() {

    inner class UpcomingLaunchesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<UpcomingLaunch>() {
        override fun areItemsTheSame(oldItem: UpcomingLaunch, newItem: UpcomingLaunch): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UpcomingLaunch, newItem: UpcomingLaunch): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingLaunchesViewHolder {
        return UpcomingLaunchesViewHolder(
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

    private var onItemClickListener: ((UpcomingLaunch) -> Unit)? = null

    override fun onBindViewHolder(holder: UpcomingLaunchesViewHolder, position: Int) {

        val pastLaunch = differ.currentList[position]

        holder.itemView.apply {
            if(pastLaunch.patch != null) {
                Glide.with(this).load(pastLaunch.patch).into(ivArticleImage)
            }

            title.text = pastLaunch.name
            description.text = pastLaunch.date_local

            setOnClickListener {
                onItemClickListener?.let { it(pastLaunch) }
            }
        }
    }

    fun setOnItemClickListener(listener: (UpcomingLaunch) -> Unit) {
        onItemClickListener = listener
    }
}













