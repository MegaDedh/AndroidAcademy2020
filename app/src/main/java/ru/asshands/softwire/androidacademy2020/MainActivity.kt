package ru.asshands.softwire.androidacademy2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import ru.asshands.softwire.androidacademy2020.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.activity_main_frame_layout, MoviesListFragment())
            .commit()
    }

}