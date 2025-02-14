package ru.flymary.app.presentation.startwindow.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.flymary.app.R
import ru.flymary.app.databinding.BannerLayoutBinding
import ru.flymary.app.model.ProductDTO

class BannerAdapter(private var onClick: (ProductDTO) -> Unit?): RecyclerView.Adapter<BAVH>() {

    private var imagesLinks: List<String> = listOf()
    private var productDTO:ProductDTO? = null

    fun setLinks(links: List<String>){
        imagesLinks = links
        notifyDataSetChanged()
    }

    fun setProduct(productDTO: ProductDTO){
        this.productDTO = productDTO
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
            Glide.with(holder.binding.bannerImage)
                .load(it)
                .into(holder.binding.bannerImage)
        }

        holder.binding.bannerImage.setOnClickListener {
            productDTO?.let { it1 -> onClick(it1) }
        }
    }


}

class BAVH(val binding: BannerLayoutBinding):  RecyclerView.ViewHolder(binding.root)