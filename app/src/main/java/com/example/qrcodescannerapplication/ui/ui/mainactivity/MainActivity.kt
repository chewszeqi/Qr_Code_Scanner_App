package com.example.qrcodescannerapplication.ui.ui.mainactivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.qrcodescannerapplication.R
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment2
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), QrScannerFragment.QrScannerFragmentListener, QrScannerFragment2.QrScannerFragment2Listener {
    private var fragment1: QrScannerFragment? = null
    private var fragment2: QrScannerFragment2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*fragment1 = QrScannerFragment()
        fragment2 = QrScannerFragment2()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_a, fragment1!!)
            .replace(R.id.container_b, fragment2!!)
            .commit()*/
        setFragment()
        setViewPager()
        setBottomNavigation()
        setViewPagerListener()
        //val qrResult = QrResult(result = "Dummy Text", resultType = "TEXT", favourite = false, calendar = Calendar.getInstance())

        //QrResultDatabase.getAppDatabase(this)?.getQrDao()?.insertQrResult(qrResult)
    }

    private fun setFragment(){
        val bundle : Bundle? = intent.extras
        val string : String? = intent.getStringExtra("Fragment")
        if(string == "Fragment A"){
            //finish()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragment1 = QrScannerFragment()
        fragmentTransaction.replace(R.id.frameLayout, fragment1!!)
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit()
            //startActivity(intent)
            clearStack()
        }

        else{

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragment2 = QrScannerFragment2()
        fragmentTransaction.replace(R.id.frameLayout2, fragment2!!)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        clearStack()
        }

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

    override fun onInputASent(input: CharSequence, formattedDate: Date, formattedTime: Date) {
        //fragment2.updateEditText(input)

    }

    override fun onInputBSent(input: CharSequence, formattedDate: Date, formattedTime: Date) {

    }




    private fun setViewPager() {
        //setFragment()
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2
    }

    private fun setBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener{
            viewPager.currentItem = when(it.itemId){
                R.id.scanMenuId -> 0
                R.id.recentScannedMenuId -> 1
                R.id.favouritesMenuId -> 2
            else -> 0
        }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setViewPagerListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView.selectedItemId = when (position) {
                    0 -> R.id.scanMenuId
                    1 -> R.id.recentScannedMenuId
                    2 -> R.id.favouritesMenuId
                    else -> R.id.scanMenuId
                }
            }

        }

        )
    }

}