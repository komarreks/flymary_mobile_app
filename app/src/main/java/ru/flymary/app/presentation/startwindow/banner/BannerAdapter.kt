package ru.flymary.app.presentation.startwindow.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.flymary.app.databinding.BannerLayoutBinding

class BannerAdapter: RecyclerView.Adapter<BAVH>() {

    private var imagesLinks: List<String> = listOf()

    fun setLinks(links: List<String>){
        imagesLinks = links
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BAVH {
        val binding = BannerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BAVH(binding)
    }

    override fun getItemCount(): Int {
        return imagesLinks.size
    }

    override fun onBindViewHolder(holder: BAVH, position: Int) {
        val imageLink = imagesLinks[position]

        imageLink?.let {
            Glide.with(holder.binding.bannerImage).load(it).into(holder.binding.bannerImage)
        }
    }
}

class BAVH(val binding: BannerLayoutBinding):  RecyclerView.ViewHolder(binding.root)