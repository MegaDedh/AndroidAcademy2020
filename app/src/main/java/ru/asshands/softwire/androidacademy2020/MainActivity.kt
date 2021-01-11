package ru.asshands.softwire.androidacademy2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.asshands.softwire.androidacademy2020.databinding.ActivityMainBinding
import ru.asshands.softwire.androidacademy2020.ui.movieslist.MoviesListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_main_frame_layout, MoviesListFragment())
                .commit()
        }
    }

}