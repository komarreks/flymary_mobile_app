package ru.flymary.app.presentation.startwindow.cataloglist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.flymary.app.R
import ru.flymary.app.databinding.CatalogLayoutBinding
import ru.flymary.app.model.CatalogDTO
import ru.flymary.app.remoteserver.RemoteServer

/**
 * Адаптер листа каталогов
 * функционльный тип - нажатие на каталоге,  выполняет переход в содержимое каталога в продуктовые категории
 */

class CatalogListAdapter(
    private val onClick: (CatalogDTO) -> Unit
):RecyclerView.Adapter<CatalogListHolder>() {

    private var catalogs: List<CatalogDTO> = listOf()

    fun update(newCatalogList: List<CatalogDTO>){
        catalogs = newCatalogList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogListHolder {
        return CatalogListHolder(CatalogLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return catalogs.size
    }

    override fun onBindViewHolder(holder: CatalogListHolder, position: Int) {
        val catalog = catalogs[position]

        holder.binding.catalogButton.text = catalog.textButton

        val imageLink = "${RemoteServer.catalogApi}${catalog.id}/image"

        Glide.with(holder.binding.catalogImage)
            .load(imageLink)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.catalogImage)

        holder.binding.root.setOnClickListener {
            onClick(catalog)
        }

        holder.binding.catalogButton.setOnClickListener {
            onClick(catalog)
        }
    }
}

class CatalogListHolder(val binding: CatalogLayoutBinding):RecyclerView.ViewHolder(binding.root)