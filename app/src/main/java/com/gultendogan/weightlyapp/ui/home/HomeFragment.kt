package com.gultendogan.weightlyapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentHomeBinding
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.ui.home.adapter.WeightHistoryAdapter
import com.gultendogan.weightlyapp.ui.home.adapter.WeightItemDecorator
import com.gultendogan.weightlyapp.utils.viewBinding
import com.yonder.statelayout.State
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val adapterWeightHistory: WeightHistoryAdapter by lazy {
        WeightHistoryAdapter(::onClickWeight)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
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
    }
    private fun setUIState(uiState: HomeViewModel.UiState) {
        if (uiState.shouldShowEmptyView){
            binding.stateLayout.setState(State.EMPTY)
        }else{
            binding.stateLayout.setState(State.CONTENT)
            adapterWeightHistory.submitList(uiState.reversedHistories)
        }
    }

    private fun initViews() {
        initWeightRecyclerview()
    }

    private fun initWeightRecyclerview() = with(binding.rvWeightHistory) {
        adapter = adapterWeightHistory
        addItemDecoration(WeightItemDecorator(requireContext()))
        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }


    private fun onClickWeight(weight: WeightUIModel) {
        findNavController().navigate(HomeFragmentDirections.actionNavigateAddWeight(weight))
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                findNavController().navigate(HomeFragmentDirections.actionNavigateAddWeight(null))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}