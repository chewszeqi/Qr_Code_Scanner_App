package com.example.qrcodescannerapplication.ui.ui.workManagerNotification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.Data
import androidx.work.ExistingWorkPolicy.REPLACE
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.qrcodescannerapplication.R
import com.example.qrcodescannerapplication.ui.ui.checkInOut.CheckInOutActivity
import com.example.qrcodescannerapplication.ui.ui.mainactivity.MainActivity
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment2
import com.example.qrcodescannerapplication.ui.ui.workManagerNotification.NotifyWork
import com.example.qrcodescannerapplication.ui.ui.workManagerNotification.NotifyWork.Companion.NOTIFICATION_ID
import com.example.qrcodescannerapplication.ui.ui.workManagerNotification.NotifyWork.Companion.NOTIFICATION_WORK
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar.make
import kotlinx.android.synthetic.main.activity_work_manager_notification.*
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault
import java.util.concurrent.TimeUnit.MILLISECONDS

class WorkManagerNotificationActivity : AppCompatActivity() {
    private var fragment1: QrScannerFragment? = null
    private var fragment2: QrScannerFragment2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager_notification)
        val bundle: Bundle? = intent.extras
        val string: String? = intent.getStringExtra("Fragment")

        if (string == "Fragment A") {
        userInterface()
        }

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
                    goToQrScannerFragment()
                    val data = Data.Builder().putInt(NOTIFICATION_ID, 0).build()
                    val delay = customTime - currentTime
                    scheduleNotification(delay, data)

                    val titleNotificationSchedule = getString(R.string.notification_schedule_title)
                    val patternNotificationSchedule =
                        getString(R.string.notification_schedule_pattern)
                    make(
                        coordinator_l,
                        titleNotificationSchedule + SimpleDateFormat(
                            patternNotificationSchedule, getDefault()
                        ).format(customCalendar.time).toString(),
                        LENGTH_LONG
                    ).show()
                    goToQrScannerFragment()
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

    fun goToQrScannerFragment(){

            //finish()
        val  intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Fragment", "Fragment C")
        startActivity(intent)
            /*val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragment1 = QrScannerFragment()
            fragmentTransaction.replace(R.id.frameLayout, fragment1!!)
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit()
            //startActivity(intent)
            clearStack()*/

    }

    fun clearStack() {
        //Here we are clearing back stack fragment entries
        val backStackEntry = supportFragmentManager.backStackEntryCount

        if (backStackEntry > 0) {
            for (i in 0 until backStackEntry) {
                supportFragmentManager.popBackStackImmediate()
            }
        }

        //Here we are removing all the fragment that are shown here
        if (supportFragmentManager.fragments != null && supportFragmentManager.fragments.size > 0) {
            for (i in supportFragmentManager.fragments.indices) {
                val mFragment: Fragment? = supportFragmentManager.fragments[i]
                if (mFragment != null) {
                    supportFragmentManager.beginTransaction().remove(mFragment).commit()
                }
            }
        }
    }
    /*override fun onBackPressed() {
        finish()
        val intent = Intent(this, CheckInOutActivity::class.java)
        startActivity(intent)
    }*/
}