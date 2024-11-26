package ru.flymary.app.presentation.startwindow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.flymary.app.R
import ru.flymary.app.databinding.FragmentStartBinding
import ru.flymary.app.presentation.startwindow.banner.BannerAdapter
import ru.flymary.app.presentation.startwindow.cataloglist.CatalogListAdapter


class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    val binding get() = _binding!!.root

    private val startModel: StartModel by viewModels()
    private val mainBannerAdapter = BannerAdapter()
    private val catalogListAdapter = CatalogListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater)
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}