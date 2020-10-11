package com.example.qrcodescannerapplication.ui.ui.mainactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.qrcodescannerapplication.R
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment
import com.example.qrcodescannerapplication.ui.ui.scanner.QrScannerFragment2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        val fragment = QrScannerFragment()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit()
            //startActivity(intent)
            clearStack()
        }

        else{
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = QrScannerFragment2()
        fragmentTransaction.replace(R.id.frameLayout2, fragment)
        fragmentTransaction.addToBackStack(null);
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