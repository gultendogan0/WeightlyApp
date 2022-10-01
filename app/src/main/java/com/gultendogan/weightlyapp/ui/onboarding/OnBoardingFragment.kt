package com.gultendogan.weightlyapp.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentOnBoardingBinding
import com.gultendogan.weightlyapp.uicomponents.CardRuler
import com.gultendogan.weightlyapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import com.gultendogan.weightlyapp.uicomponents.MeasureUnit

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {

    private val binding by viewBinding(FragmentOnBoardingBinding::bind)

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    OnBoardingViewModel.Event.NavigateToHome -> {
                        findNavController().navigate(OnBoardingFragmentDirections.actionNavigateHome())
                    }
                }
            }
        }
    }


    private fun initViews() = with(binding) {
        cardRulerCurrent.render(CardRuler(unit = R.string.kg, hint = R.string.enter_current_weight))
        cardRulerGoal.render(CardRuler(unit = R.string.kg, hint = R.string.enter_goal_weight))
        cardRulerHeight.render(
            CardRuler(
                unit = R.string.cm,
                hint = R.string.enter_current_height,
                num = 175f,
                max = 250
            )
        )
        btnContinue.setOnClickListener {
            val currentWeight: Float = cardRulerCurrent.value
            val goalWeight: Float = cardRulerGoal.value
            val unit = if (toggleButton.checkedButtonId == R.id.button1) {
                MeasureUnit.KG
            } else {
                MeasureUnit.LB
            }
            viewModel.save(currentWeight = currentWeight, goalWeight = goalWeight, unit = unit)
        }
        toggleButton.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (!isChecked)
                return@addOnButtonCheckedListener
            if (checkedId == R.id.button1) {
                cardRulerCurrent.setUnit(MeasureUnit.KG)
                cardRulerGoal.setUnit(MeasureUnit.KG)
            } else {
                cardRulerCurrent.setUnit(MeasureUnit.LB)
                cardRulerGoal.setUnit(MeasureUnit.LB)
            }
        }
    }

}