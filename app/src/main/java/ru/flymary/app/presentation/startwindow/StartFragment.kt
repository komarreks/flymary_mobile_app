package ru.flymary.app.presentation.startwindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flymary.app.App
import ru.flymary.app.R
import ru.flymary.app.databinding.FragmentStartBinding
import ru.flymary.app.model.CatalogDTO
import ru.flymary.app.presentation.startwindow.banner.BannerAdapter
import ru.flymary.app.presentation.startwindow.cataloglist.CatalogListAdapter
import ru.flymary.app.values.NavTypes

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    val binding get() = _binding!!.root

    private val startModel: StartModel by viewModels()
    private val mainBannerAdapter = BannerAdapter{}
    private val catalogListAdapter = CatalogListAdapter { catalogDTO -> onCatalogClick(catalogDTO) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater)
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val casheCleaner = Thread{
            if (App.FIRST_START){
                Glide.get(requireContext()).clearDiskCache()
                App.FIRST_START = false
            }
        }.start()

        _binding?.bannerPager?.adapter = mainBannerAdapter

        _binding?.dotsIndicator?.setViewPager2(_binding!!.bannerPager)

        _binding?.catalogsRecycle?.adapter = catalogListAdapter

        startModel.imagesForBanner.onEach {
            mainBannerAdapter.setLinks(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        startModel.catalogs.onEach {
            catalogListAdapter.update(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onCatalogClick(catalogDTO: CatalogDTO){
        val bundle = Bundle()
        bundle.putString(NavTypes.CATALOG_ID, catalogDTO.id)
        bundle.putString(NavTypes.CATALOG_NAME, catalogDTO.name)
        findNavController().navigate(R.id.action_start_fragment_to_catalogFragment, bundle)
    }
}