package com.example.midterm22b1num0027.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

fun scheduleReminder(context: Context) {
    val request= PeriodicWorkRequestBuilder<WordReminderWorker>(24,TimeUnit.HOURS).build()
    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork("word_reminder", ExistingPeriodicWorkPolicy.UPDATE, request)
}
fun cancelReminder(context: Context) {
    val workManager = WorkManager.getInstance(context)
    workManager.cancelUniqueWork("word_reminder")
}

