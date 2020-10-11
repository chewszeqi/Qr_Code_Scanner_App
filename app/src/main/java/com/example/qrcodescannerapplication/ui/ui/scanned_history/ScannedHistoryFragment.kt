package com.example.qrcodescannerapplication.ui.ui.scanned_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qrcodescannerapplication.R

/**
 * A simple [Fragment] subclass.
 * Use the [ScannedHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScannedHistoryFragment : Fragment() {

    companion object{
        fun newInstance(): ScannedHistoryFragment{
            return ScannedHistoryFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanned_history, container, false)
    }
}