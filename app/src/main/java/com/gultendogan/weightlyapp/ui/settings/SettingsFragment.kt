package com.gultendogan.weightlyapp.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.gultendogan.weightlyapp.R
import androidx.preference.CheckBoxPreference
import com.gultendogan.weightlyapp.BuildConfig
import androidx.lifecycle.lifecycleScope
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import com.gultendogan.weightlyapp.uicomponents.MeasureUnit
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preference, rootKey)
        val goalWeightPreference = findPreference<EditTextPreference>("weight")
        goalWeightPreference?.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
        goalWeightPreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue is String) {
                val weight = newValue.toFloatOrNull()
                if (weight != null) {
                    viewModel.updateGoalWeight(weight)
                    preference.summary = newValue
                } else {
                    Toast.makeText(context,"Invalid value!",Toast.LENGTH_LONG).show()
                }
            }
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initViews()
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect(::setUIState)
        }
    }

    private fun setUIState(uiState: SettingsViewModel.UiState) {
        val unitPreferences = findPreference<ListPreference>("unit")
        val limitLinePreference = findPreference<CheckBoxPreference>("show_limit_lines")
        val goalWeightPreference = findPreference<EditTextPreference>("weight")

        limitLinePreference?.isChecked = uiState.shouldShowLimitLine
        unitPreferences?.value = MeasureUnit.findValue(uiState.unit).value

        goalWeightPreference?.summaryProvider = null
        goalWeightPreference?.summary = uiState.goalWeight.toString()
    }

    private fun initViews() {
        findPreference<Preference>("developer")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openUrl("https://github.com/gulten27")
                true
            }
        findPreference<Preference>("source_code")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openUrl("https://github.com/gulten27/WeightlyApp")
                true
            }
        findPreference<Preference>("send_feedback")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openUrl("https://forms.gle/kNxxSE4SS1xy2qRt7")
                true
            }

        findPreference<Preference>("rate_us")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                    )
                )
                true
            }

        findPreference<ListPreference>("unit")?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is String) {
                viewModel.updateUnit(newValue)
            }
            true
        }

        findPreference<CheckBoxPreference>("show_limit_lines")?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is Boolean) {
                viewModel.updateLimitLine(newValue)
            }
            true
        }

    }

    private fun openUrl(url : String){
        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse(url)
        )
        startActivity(viewIntent)
    }

}