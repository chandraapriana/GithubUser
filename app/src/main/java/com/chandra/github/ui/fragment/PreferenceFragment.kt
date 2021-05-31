package com.chandra.github.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.chandra.github.R
import com.chandra.github.databinding.FragmentPreferenceBinding


class PreferenceFragment : Fragment() {

    private lateinit var binding: FragmentPreferenceBinding

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreferenceBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarPreference.btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
        activity?.supportFragmentManager?.commit {
            replace(R.id.container_setting, SettingsFragment())
        }

    }

}


