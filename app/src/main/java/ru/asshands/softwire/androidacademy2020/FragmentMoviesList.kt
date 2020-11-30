package ru.asshands.softwire.androidacademy2020

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.asshands.softwire.androidacademy2020.databinding.FragmentMoviesListBinding

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private var _bind: FragmentMoviesListBinding? = null
    private val bind get() = _bind!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _bind = FragmentMoviesListBinding.bind(view)


        bind.includeMovieItem1.moviesListItemCard.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_main_frame_layout, FragmentMovieDetails())
                .addToBackStack(null)
                .commit()
        }


    bind.includeMovieItem2.moviesListItemCard.setOnClickListener {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .add(R.id.activity_main_frame_layout, FragmentMovieDetails())
            .addToBackStack(null)
            .commit()
    }
}

    override fun onDestroyView() {
        _bind = null
        super.onDestroyView()
    }
}