package ru.flymary.app.presentation.startwindow.productswindow

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import ru.flymary.app.R
import ru.flymary.app.databinding.ProductLayoutBinding
import ru.flymary.app.model.CharacDTO
import ru.flymary.app.model.ProductDTO
import ru.flymary.app.presentation.startwindow.banner.BannerAdapter


class ProductsAdapter: RecyclerView.Adapter<Holder>() {

    private var products: List<ProductDTO> = listOf()
    private var characsButtonsId: MutableMap<Int, CharacDTO> = mutableMapOf()

    fun setProducts(productDTOs: List<ProductDTO>){
        products = productDTOs
        characsButtonsId.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val holder = Holder(ProductLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        holder.binding.productImage.adapter = BannerAdapter()
        return holder
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val productDTO = products[position]

        holder.binding.productName.text = productDTO.name

        holder.binding.characsPane.removeAllViews()

        createCharacsButtons(holder, productDTO.characTDOs, productDTO)

        if (position == products.size - 1) {
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.bottomMargin = 400 // last item bottom margin
            holder.itemView.layoutParams = params
        }
    }

    private fun createCharacsButtons(holder: Holder, characs: List<CharacDTO>, productDTO: ProductDTO){

        var countCharacButtons = 0
        var firstButton = true

        for (charac in characs){
            val tb = newToggleButton(holder, charac.name, characsButtonsId.size + 1)//ToggleButton(binding.root.context)

            if (firstButton){
                tb.isChecked = true
                firstButton = false
                (holder.binding.productImage.adapter as BannerAdapter).setLinks(charac.imageUrl)
                updateProductNameAndPrice(holder, productDTO.name, charac)
            }

            initOnClick(tb, holder)

            characsButtonsId.put(tb.id, charac)

            countCharacButtons++

            if (countCharacButtons == 2)break
        }

        if (!characs.isEmpty() && characs.size > 2){
            val tb = newToggleButton(holder, "еще...", 333)

        }
    }

    private fun updateProductNameAndPrice(holder: Holder, productName: String, charac:CharacDTO){
        holder.binding.characName.text = " (${charac.name})"
        holder.binding.price.text = charac.price.toString()
    }

    private fun newToggleButton(holder: Holder, name:String, id: Int): ToggleButton{
        val tb = ToggleButton(holder.binding.root.context)
        tb.id = id
        tb.text = name
        tb.textOn = name
        tb.textOff = name
        tb.background = ResourcesCompat.getDrawable(holder.binding.root.context.resources, R.drawable.filter_button_selector, null)
        tb.setTextColor(holder.binding.root.context.resources.getColorStateList(R.color.filter_button_selector, null))
        tb.isAllCaps = false

        holder.binding.characsPane.addView(tb)

        val lp = tb.layoutParams as ViewGroup.MarginLayoutParams
        lp.setMargins(0,0,10,0)
        lp.height = 90
        lp.width = 170
        tb.layoutParams = lp

        tb.setPadding(15,0,15,0)

        return tb
    }

    private fun initOnClick(tb: ToggleButton, holder: Holder){
        tb.setOnClickListener {
            val tbs = (tb.parent as LinearLayout).children

            for(tbch in tbs){
                if (tbch.id != tb.id){
                    (tbch as ToggleButton).isChecked = false
                }
            }

            tb.isChecked = true

            var images = characsButtonsId[tb.id]?.imageUrl?.toList()

            if (images == null)images = listOf()
            (holder.binding.productImage.adapter as BannerAdapter).setLinks(images)
            holder.binding.characName.text = " (${tb.text})"
            holder.binding.price.text = characsButtonsId[tb.id]?.price.toString()
        }
    }
}

class Holder(val binding: ProductLayoutBinding): RecyclerView.ViewHolder(binding.root){
}