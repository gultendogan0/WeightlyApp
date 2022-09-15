package com.gultendogan.weightlyapp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentAddWeightBinding
import com.gultendogan.weightlyapp.utils.extensions.toFormat
import com.gultendogan.weightlyapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

//gultendogan.weightlyapp

const val CURRENT_DATE_FORMAT = "dd MMM yyyy"
const val TAG_DATE_PICKER = "Tag_Date_Picker"

@AndroidEntryPoint
class AddWeightFragment : BottomSheetDialogFragment() {

    private val viewModel: AddWeightViewModel by viewModels()

    private var selectedDate = Date()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_weight, container, false)

    private val binding by viewBinding(FragmentAddWeightBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        btnSelectDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.select_date))
                    .setSelection(selectedDate.time)
                    .build()
            datePicker.addOnPositiveButtonClickListener { timestamp ->
                selectedDate = Date(timestamp)
                btnSelectDate.text = selectedDate.toFormat(CURRENT_DATE_FORMAT)
            }
            datePicker.show(parentFragmentManager, TAG_DATE_PICKER);
        }

        btnSelectDate.text = selectedDate.toFormat(CURRENT_DATE_FORMAT)

        btnSave.setOnClickListener {
            val weight = tilInputWeight.text.toString()
            val note = tilInputNote.text.toString()
            viewModel.addWeight(weight = weight, note = note, date = selectedDate)
        }
    }

}