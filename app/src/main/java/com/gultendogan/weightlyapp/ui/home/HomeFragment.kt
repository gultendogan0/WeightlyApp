package com.gultendogan.weightlyapp.ui.home

//gultendogan.weightlyapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentHomeBinding
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.ui.add.AddWeightFragment
import com.gultendogan.weightlyapp.ui.home.adapter.WeightHistoryAdapter
import com.gultendogan.weightlyapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels()
    private val adapterWeightHistory: WeightHistoryAdapter by lazy {
        WeightHistoryAdapter(::onClickWeight)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect(::setUIState)
        }
        setFragmentResultListener(AddWeightFragment.KEY_SHOULD_FETCH_WEIGHT_HISTORY) { _, _ ->
            viewModel.getAllWeightHistory()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllWeightHistory()
    }

    private fun setUIState(uiState: HomeViewModel.UiState) {
        adapterWeightHistory.submitList(uiState.histories)
    }

    private fun initViews() = with(binding) {
        rvWeightHistory.adapter = adapterWeightHistory
        binding.btnFab.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_add_weight)
        }
    }
    private fun onClickWeight(weight: WeightUIModel) {
    }
}
