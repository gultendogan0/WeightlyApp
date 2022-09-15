package com.gultendogan.weightlyapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentHomeBinding
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.ui.home.adapter.WeightHistoryAdapter
import com.gultendogan.weightlyapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

    private val adapterWeightHistory : WeightHistoryAdapter by lazy {
        WeightHistoryAdapter(::onClickWeight)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun observe(){

    }

    private fun initViews(){
        binding.rvWeightHistory.adapter = adapterWeightHistory.apply {

        }
    }

    private fun onClickWeight(weight : WeightUIModel){

    }
}