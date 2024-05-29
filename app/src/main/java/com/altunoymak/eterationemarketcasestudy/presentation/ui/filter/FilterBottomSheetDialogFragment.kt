package com.altunoymak.eterationemarketcasestudy.presentation.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.altunoymak.eterationemarketcasestudy.databinding.FragmentFilterBottomSheetDialogBinding
import com.altunoymak.eterationemarketcasestudy.presentation.ui.home.ProductViewModel
import com.altunoymak.eterationemarketcasestudy.util.SortBy
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
        binding.filterToolbar.navigationIconSetOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}