package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder().putInt("intKey",1).build()

        val constaints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

      /*  val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constaints)
            .setInputData(data)
            //.setInitialDelay(5,TimeUnit.HOURS)
            //.addTag("myTag")
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
        */

        val myWorkRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15,TimeUnit.MINUTES)
            .setConstraints(constaints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this,
            Observer {

                if (it.state==WorkInfo.State.RUNNING){
                    println("running")
                }else if (it.state==WorkInfo.State.FAILED){
                    println("failed")
                }else if (it.state==WorkInfo.State.SUCCEEDED)
                    println("succeeded")
            })

       // WorkManager.getInstance(this).cancelAllWork()

        //Chaining

        /*

        val oneTimeRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constaints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).beginWith(oneTimeRequest).then(oneTimeRequest)
            .then(oneTimeRequest)
            .enqueue()

            */
         

    }
}