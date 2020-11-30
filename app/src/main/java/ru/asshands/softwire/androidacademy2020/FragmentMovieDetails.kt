package ru.asshands.softwire.androidacademy2020

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMovieDetailsBinding

class FragmentMovieDetails : Fragment(R.layout.fragment_movie_details) {

    private var _bind: FragmentMovieDetailsBinding? = null
    private val bind get() = _bind!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentMovieDetailsBinding.bind(view)


        bind.fragmentMovieDetailsBackBackText.setOnClickListener {
            requireActivity().supportFragmentManager
                .popBackStack()
        }
    }

    override fun onDestroyView() {
        _bind = null
        super.onDestroyView()
    }
}