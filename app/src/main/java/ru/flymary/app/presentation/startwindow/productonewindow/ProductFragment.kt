package ru.flymary.app.presentation.startwindow.productonewindow

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ToggleButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flymary.app.R
import ru.flymary.app.databinding.FragmentProductBinding
import ru.flymary.app.databinding.FragmentProductsBinding
import ru.flymary.app.model.CharacDTO
import ru.flymary.app.model.ProductDTO
import ru.flymary.app.presentation.startwindow.banner.BannerAdapter
import ru.flymary.app.presentation.startwindow.productswindow.Holder
import ru.flymary.app.values.NavTypes

class ProductFragment : Fragment() {

    private var productId: String? = null

    private lateinit var model: ProductModel

    private var _binding: FragmentProductBinding? = null
    val binding get() = _binding!!

    private val characButtons: MutableMap<Int, CharacDTO> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getString(NavTypes.PRODUCT_ID)
            productId?.let { id -> model = ProductModel(id) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.productImage.adapter = BannerAdapter{}

        model.productDTO.onEach {
            if (it != null){
                createPage(it)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun createPage(productDTO: ProductDTO) {
        binding.productName.text = productDTO.name

        binding.characsPane.removeAllViews()

        if (productDTO.characTDOs.isNotEmpty()){
            var idCounter:Int = 1
            characButtons.clear()
            var firstButton = true
            for (characDTO in productDTO.characTDOs){
                val tb = newToggleButton(characDTO.name, idCounter)
                characButtons.put(idCounter, characDTO)

                initOnClick(tb)

                idCounter++

                if (firstButton){
                    tb.toggle()
                    binding.price.text =
                        characButtons[tb.id]?.price?.let { it1 -> formatPrice(it1, requireContext()) }
                    firstButton = false

                    loadImages(characDTO.imageUrl.toList())
                }
            }
        }else{
            binding.price.text = formatPrice(productDTO.price,requireContext())
            loadImages(productDTO.imageUrl)
        }
    }

    private fun newToggleButton(name:String, id: Int): ToggleButton {
        val tb = ToggleButton(requireContext())
        tb.id = id
        tb.text = name
        tb.textOn = name
        tb.textOff = name
        tb.background = ResourcesCompat.getDrawable(resources, R.drawable.filter_button_selector, null)
        tb.setTextColor(resources.getColorStateList(R.color.filter_button_selector, null))
        tb.isAllCaps = false

        binding.characsPane.addView(tb)

        val lp = tb.layoutParams as ViewGroup.MarginLayoutParams
        lp.setMargins(0,0,10,0)
        lp.height = 90
        lp.width = 170
        tb.layoutParams = lp

        tb.setPadding(15,0,15,0)

        return tb
    }

    private fun loadImages(images: List<String>){
        (binding.productImage.adapter as BannerAdapter).setLinks(images)
    }

    private fun initOnClick(tb: ToggleButton){
        tb.setOnClickListener {
            val tbs = (tb.parent as LinearLayout).children

            for(tbch in tbs){
                if (tbch.id != tb.id){
                    (tbch as ToggleButton).isChecked = false
                }
            }

            tb.isChecked = true

            characButtons[tb.id]?.imageUrl?.toList()?.let { it1 -> loadImages(it1) }

            binding.price.text =
                characButtons[tb.id]?.price?.let { it1 -> formatPrice(it1, requireContext()) }
        }
    }

    private fun formatPrice(price: Double, context: Context): String{
        val afterDrop:Double = price-price.toInt()

        if (afterDrop > 0){
            return price.toString() + " " + context.resources.getString(R.string.rubl)
        }

        return price.toInt().toString() + " " + context.resources.getString(R.string.rubl)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(productId: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(NavTypes.PRODUCT_ID, productId)
                }
            }
    }
}