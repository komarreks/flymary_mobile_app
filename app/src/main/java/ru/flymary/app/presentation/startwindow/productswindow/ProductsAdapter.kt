package ru.flymary.app.presentation.startwindow.productswindow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.flymary.app.databinding.FragmentProductsBinding
import ru.flymary.app.databinding.NodeElementBinding
import ru.flymary.app.databinding.ProductLayoutBinding
import ru.flymary.app.model.ProductDTO
import ru.flymary.app.presentation.startwindow.catalogwindow.holder

class ProductsAdapter: RecyclerView.Adapter<Holder>() {

    private var products: List<ProductDTO> = listOf()

    fun setProducts(productDTOs: List<ProductDTO>){
        products = productDTOs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ProductLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val productDTO = products[position]

        holder.binding.productName.text = productDTO.name
    }
}

class Holder(val binding: ProductLayoutBinding): RecyclerView.ViewHolder(binding.root)