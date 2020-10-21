package com.example.qrcodescannerapplication.ui.ui.checkInOut

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.qrcodescannerapplication.R
import com.example.qrcodescannerapplication.ui.ui.mainactivity.MainActivity
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment2
import com.example.qrcodescannerapplication.ui.ui.workManagerNotification.WorkManagerNotificationActivity

class CheckInOutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in_out)

        val checkIn = findViewById<Button>(R.id.checkIn)
        checkIn?.setOnClickListener()
        {
            goToMainActivity1()

        }

        val checkOut = findViewById<Button>(R.id.checkOut)
        checkOut?.setOnClickListener()
        {
            goToMainActivity2()

        }
    }

    private fun goToMainActivity1() {
        val  intent = Intent(this, WorkManagerNotificationActivity::class.java)
        intent.putExtra("Fragment", "Fragment A")
        startActivity(intent)
        //startActivity(Intent(this,MainActivity::class.java))
        /*val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = QrScannerFragment()
        fragmentTransaction.add(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit()
        startActivity(Intent(this,MainActivity::class.java))*/

    }

    private fun goToMainActivity2() {
        val  intent = Intent(this, MainActivity::class.java )
        intent.putExtra("Fragment", "Fragment B")
        startActivity(intent)
        //startActivity(Intent(this,MainActivity::class.java))
        /*val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = QrScannerFragment2()
        fragmentTransaction.add(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit()
        startActivity(Intent(this, MainActivity::class.java))*/
    }
}

