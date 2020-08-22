package com.example.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDatabase(val context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {

        val getData = inputData
        val myNumber = getData.getInt("intKey",0)

        refreshDatabase(myNumber)

        return  Result.success()
    }

    private fun refreshDatabase(myNumber:Int){
        val sharedPreferences= context.getSharedPreferences("com.example.workmanager",Context.MODE_PRIVATE)
        var mySaveNumber = sharedPreferences.getInt("myNumber",0)
        mySaveNumber= mySaveNumber + myNumber
        println(mySaveNumber)
        sharedPreferences.edit().putInt("myNumber",mySaveNumber).apply()
    }
}