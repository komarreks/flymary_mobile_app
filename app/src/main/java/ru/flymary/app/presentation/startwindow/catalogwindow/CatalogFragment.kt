package ru.flymary.app.presentation.startwindow.catalogwindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.flymary.app.databinding.FragmentCatalogBinding
import ru.flymary.app.values.NavTypes

class CatalogFragment : Fragment() {

    private var catalogId: String? = null
    private var catalogName: String? = null

    private var _binding:FragmentCatalogBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            catalogId = it.getString(NavTypes.CATALOG_ID)
            catalogName = it.getString(NavTypes.CATALOG_NAME)
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
        binding.text.text = catalogName
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