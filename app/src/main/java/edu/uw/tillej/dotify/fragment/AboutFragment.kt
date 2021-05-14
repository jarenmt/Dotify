package edu.uw.tillej.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.uw.tillej.dotify.BuildConfig
import edu.uw.tillej.dotify.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAboutBinding.inflate(inflater)


        with(binding) {
            version.text = BuildConfig.VERSION_NAME
        }

        return binding.root
    }
}