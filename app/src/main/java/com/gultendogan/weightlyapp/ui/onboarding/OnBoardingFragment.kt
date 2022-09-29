package com.gultendogan.weightlyapp.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentOnBoardingBinding
import com.gultendogan.weightlyapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {

    private val binding by viewBinding(FragmentOnBoardingBinding::bind)

    private val viewModel: OnBoardingViewModel by viewModels()

}