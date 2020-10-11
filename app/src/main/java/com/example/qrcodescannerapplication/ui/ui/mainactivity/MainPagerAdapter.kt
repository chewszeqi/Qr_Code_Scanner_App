package com.example.qrcodescannerapplication.ui.ui.mainactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.qrcodescannerapplication.R
import com.example.qrcodescannerapplication.ui.ui.scanned_history.ScannedHistoryFragment
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment2

class MainPagerAdapter(var fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> QrScannerFragment2.newInstance()
            0 -> QrScannerFragment.newInstance()
            1 -> ScannedHistoryFragment.newInstance()
            2 -> ScannedHistoryFragment.newInstance()
            else -> QrScannerFragment.newInstance()
        }
}
    override fun getCount(): Int {
        return 3
    }
}