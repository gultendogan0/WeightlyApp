package com.gultendogan.weightlyapp.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentSettingsBinding
import com.gultendogan.weightlyapp.ui.splash.SplashViewModel
import com.gultendogan.weightlyapp.utils.viewBinding
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import com.gultendogan.weightlyapp.uicomponents.MeasureUnit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preference, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect(::setUIState)
        }
    }

    private fun setUIState(uiState: SettingsViewModel.UiState) {
        val unitPreferences = findPreference<ListPreference>("unit")
        unitPreferences?.value = MeasureUnit.findValue(uiState.unit).value
    }

    private fun initViews() {
        findPreference<Preference>("developer")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openUrl("https://www.linkedin.com/in/gülten-doğan-3a453721b/")
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

        findPreference<ListPreference>("unit")?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is String) {
                viewModel.updateUnit(newValue)
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