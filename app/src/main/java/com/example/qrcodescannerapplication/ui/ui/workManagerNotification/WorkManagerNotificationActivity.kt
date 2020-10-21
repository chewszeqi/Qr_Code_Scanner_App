package com.example.qrcodescannerapplication.ui.ui.workManagerNotification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.ExistingWorkPolicy.REPLACE
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.qrcodescannerapplication.R
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make
import kotlinx.android.synthetic.main.activity_main.*
import com.example.qrcodescannerapplication.ui.ui.workManagerNotification.NotifyWork
import com.example.qrcodescannerapplication.ui.ui.workManagerNotification.NotifyWork.Companion.NOTIFICATION_ID
import com.example.qrcodescannerapplication.ui.ui.workManagerNotification.NotifyWork.Companion.NOTIFICATION_WORK
import kotlinx.android.synthetic.main.activity_work_manager_notification.*
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault
import java.util.concurrent.TimeUnit.MILLISECONDS
import kotlin.jvm.java

class WorkManagerNotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInterface()
    }

    private fun userInterface() {
        setSupportActionBar(toolbar)

        val titleNotification = getString(R.string.notification_title)
        collapsing_toolbar_l.title = titleNotification

        done_fab.setOnClickListener {
            val customCalendar = Calendar.getInstance()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                customCalendar.set(
                    date_p.year, date_p.month, date_p.dayOfMonth, time_p.hour, time_p.minute, 0
                )
            }
            val customTime = customCalendar.timeInMillis
            val currentTime = currentTimeMillis()
            if (customTime > currentTime) {
                val data = Data.Builder().putInt(NOTIFICATION_ID, 0).build()
                val delay = customTime - currentTime
                scheduleNotification(delay, data)

                val titleNotificationSchedule = getString(R.string.notification_schedule_title)
                val patternNotificationSchedule = getString(R.string.notification_schedule_pattern)
                make(
                    coordinator_l,
                    titleNotificationSchedule + SimpleDateFormat(
                        patternNotificationSchedule, getDefault()
                    ).format(customCalendar.time).toString(),
                    LENGTH_LONG
                ).show()
            } else {
                val errorNotificationSchedule = getString(R.string.notification_schedule_error)
                make(coordinator_l, errorNotificationSchedule, LENGTH_LONG).show()
            }
        }
    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(this)
        instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, REPLACE, notificationWork).enqueue()
    }
}