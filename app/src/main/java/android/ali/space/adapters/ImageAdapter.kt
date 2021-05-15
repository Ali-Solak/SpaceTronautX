package android.ali.space.adapters

import android.ali.space.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.card_item.view.*

class ImageAdapter(private val imageArray: List<String>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(cardItem: View) : RecyclerView.ViewHolder(cardItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageArray[position]

        holder.itemView.apply {
            Glide.with(this).load(image).apply(RequestOptions.placeholderOf(R.drawable.progress_indi)).into(bannerIv)
        }
    }

    override fun getItemCount(): Int {
        return imageArray.size
    }


}


