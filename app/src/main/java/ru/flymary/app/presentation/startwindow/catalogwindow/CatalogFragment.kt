package ru.flymary.app.presentation.startwindow.catalogwindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flymary.app.databinding.FragmentCatalogBinding
import ru.flymary.app.values.NavTypes

class CatalogFragment : Fragment() {

    private var catalogId: String? = null
    private var catalogName: String? = null

    private var _binding:FragmentCatalogBinding? = null
    val binding get() = _binding!!

    private lateinit var  model: CatalogModel
    private val nodesAdapter = NodeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            catalogId = it.getString(NavTypes.CATALOG_ID)
            catalogName = it.getString(NavTypes.CATALOG_NAME)
            catalogId?.let { id -> model = CatalogModel(id) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.catalogName.text = catalogName
        binding.nodes.adapter = nodesAdapter

        model.nodes.onEach {
            nodesAdapter.setNodes(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String, name: String) =
            CatalogFragment().apply {
                arguments = Bundle().apply {
                    putString(NavTypes.CATALOG_ID, id)
                    putString(NavTypes.CATALOG_NAME, name)
                }
            }
    }
}