package ru.flymary.app.presentation.startwindow.productswindow

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flymary.app.R
import ru.flymary.app.databinding.FragmentProductsBinding
import ru.flymary.app.model.NodeDTO
import ru.flymary.app.values.NavTypes

class ProductsFragment : Fragment() {

    private var node_id: String? = null
    private var node_name: String? = null

    private lateinit var model: ProductsListModel
    private val adapter = ProductsAdapter()

    private val filterMap: MutableMap<Int, NodeDTO> = mutableMapOf()

    private var _binding: FragmentProductsBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            node_id = it.getString(NavTypes.NODE_ID)
            node_name = it.getString(NavTypes.NODE_NAME)
            node_id?.let { id -> model = ProductsListModel(id) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nodeName.text = node_name
        binding.productsList.adapter = adapter

        model.filters.onEach {
            createFilters(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        model.products.onEach {
            adapter.setProducts(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun createFilters(filters: List<NodeDTO>) {
        binding.filterPanel.removeAllViews()
        filterMap.clear()

        var filterId = 1

        for (filter in filters) {
            val toggleButton = ToggleButton(requireContext())
            toggleButton.background = ResourcesCompat.getDrawable(resources, R.drawable.filter_button_selector, null)
            toggleButton.id = filterId
            toggleButton.text = filter.name
            toggleButton.textOn = "${filter.name}  x"
            toggleButton.textOff = filter.name

            toggleButton.setTextColor(resources.getColorStateList(R.color.filter_button_selector, null))

            binding.filterPanel.addView(toggleButton)

            val lp = toggleButton.layoutParams as ViewGroup.MarginLayoutParams
            lp.setMargins(0,0,10,0)
            lp.height = 70
            toggleButton.layoutParams = lp

            toggleButton.setPadding(15,0,15,0)

            filterMap.put(filterId, filter)

            filterId++

            toggleButton.setOnCheckedChangeListener { button, isCheck ->
                val countFilters = binding.filterPanel.children

                val filterStringList:MutableList<String> = mutableListOf()

                for (filter in countFilters){
                    if ((filter as ToggleButton).isChecked){
                        filterMap[filter.id]?.let { filterStringList.add(it.id) }
                    }
                }

                model.filterProducts(filterStringList)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(node_id: String, node_name: String) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(NavTypes.NODE_ID, node_id)
                    putString(NavTypes.NODE_NAME, node_name)
                }
            }
    }
}