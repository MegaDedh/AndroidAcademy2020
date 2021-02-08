package ru.asshands.softwire.androidacademy2020.ui.movieslist

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MovieWorker (context: Context, params: WorkerParameters): Worker(context, params){
    override fun doWork(): Result {

        for (attempt in 0..10){
            Log.d("MyWorker", "Attempt to connect. Attempts: $attempt")
            Thread.sleep(1000)
                return Result.success()
//            if(ConnectionChecker.isOnline()){
//                return Result.success()
//            }
        }
        return Result.failure()
    }
}