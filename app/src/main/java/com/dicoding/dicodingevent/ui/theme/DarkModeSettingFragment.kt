package com.dicoding.dicodingevent.ui.theme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.DarkModeViewModelFactory
import com.dicoding.dicodingevent.databinding.FragmentDarkModeSettingBinding


class DarkModeSettingFragment : Fragment() {

    private var _binding: FragmentDarkModeSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //init binding
        _binding = FragmentDarkModeSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //mengambil preferences
        val preferences = DarkModePreferences.getInstance(requireContext().dataStore)

        //init view model
        val darkModeViewModel = ViewModelProvider(this, DarkModeViewModelFactory(preferences))[DarkModeViewModel::class.java]

        //observe data for Dark Mode
        observeDarkMode(darkModeViewModel)

        //set onChecked
        binding.buttonDarkMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            darkModeViewModel.saveDarkModeSetting(isChecked)
        }

        return root
    }

    private fun observeDarkMode(darkModeViewModel: DarkModeViewModel) {
        // Cek mode saat ini dan atur status tombol
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        binding.buttonDarkMode.isChecked = currentNightMode == AppCompatDelegate.MODE_NIGHT_YES

        //setting button dark mode
        darkModeViewModel.getDarkModeSetting().observe(viewLifecycleOwner) {isDarkModeActive: Boolean ->
            //set dark mode based on switch status
            if (isDarkModeActive) {
                //apabila mode dark mode non aktif
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.buttonDarkMode.isChecked = true
            } else {
                //apabila mode dark mode aktif
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.buttonDarkMode.isChecked = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Bersihkan binding
        _binding = null
    }
}