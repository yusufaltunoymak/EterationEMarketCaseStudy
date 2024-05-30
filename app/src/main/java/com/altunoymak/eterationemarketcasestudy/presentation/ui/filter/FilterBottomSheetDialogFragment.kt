package com.altunoymak.eterationemarketcasestudy.presentation.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.altunoymak.eterationemarketcasestudy.databinding.FragmentFilterBottomSheetDialogBinding
import com.altunoymak.eterationemarketcasestudy.presentation.ui.ProductViewModel
import com.altunoymak.eterationemarketcasestudy.util.SortBy
import com.altunoymak.eterationemarketcasestudy.util.clickWithDebounce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterBottomSheetDialogFragment : Fragment() {
    private lateinit var sortByAdapter: SortByAdapter
    private lateinit var brandAdapter: BrandAdapter
    private lateinit var modelAdapter: ModelAdapter
    private val productViewModel: ProductViewModel by activityViewModels()
    private var _binding: FragmentFilterBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortByAdapter = SortByAdapter()
        brandAdapter = BrandAdapter()
        modelAdapter = ModelAdapter()
        setupSearchViews()

        binding.sortByRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.brandRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.modelRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.sortByRv.adapter = sortByAdapter
        binding.brandRecyclerView.adapter = brandAdapter
        binding.modelRecyclerView.adapter = modelAdapter

        sortByAdapter.submitList(SortBy.entries)
        viewLifecycleOwner.lifecycleScope.launch {
            productViewModel.viewState.collect { state ->
                brandAdapter.submitList(state.brandList)
                modelAdapter.submitList(state.modelList)
            }
        }
        binding.closeButton.clickWithDebounce {
            it.findNavController().popBackStack()
        }
        binding.applyFiltersButton.setOnClickListener {
            productViewModel.selectedSortBy.value = sortByAdapter.getSelectedSortBy()
            val selectedBrands = brandAdapter.getSelectedBrands()
            productViewModel.selectedBrands.value = selectedBrands
            val selectedModels = modelAdapter.getSelectedModels()
            productViewModel.selectedModels.value = selectedModels
            findNavController().popBackStack()
        }
    }

    private fun getFilteredBrandNames(searchText: String): List<String> {
        val allBrands = productViewModel.viewState.value.brandList
        return allBrands.filter { it.contains(searchText, ignoreCase = true) }
    }

    private fun getFilteredModelNames(searchText: String): List<String> {
        val allModels = productViewModel.viewState.value.modelList
        return allModels.filter { it.contains(searchText, ignoreCase = true) }
    }

    private fun setupSearchViews() {
        binding.brandSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredBrandNames = getFilteredBrandNames(it)
                    brandAdapter.submitList(filteredBrandNames)
                }
                return true
            }
        })

        binding.modelSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredModelNames = getFilteredModelNames(it)
                    modelAdapter.submitList(filteredModelNames)
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}