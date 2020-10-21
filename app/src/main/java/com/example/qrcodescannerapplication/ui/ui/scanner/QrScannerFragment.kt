package com.example.qrcodescannerapplication.ui.ui.scanner

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.qrcodescannerapplication.R
import com.example.qrcodescannerapplication.ui.ui.dialog.QrCodeResultDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_qr_scanner.view.*
import kotlinx.android.synthetic.main.layout_qr_result_show.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [QrScannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QrScannerFragment : Fragment(), ZXingScannerView.ResultHandler {

    companion object {
        private val Tag = QrScannerFragment::class.simpleName
        fun newInstance(): QrScannerFragment {
            return QrScannerFragment()
        }
    }

    private lateinit var mView: View

    private lateinit var scannerView : ZXingScannerView

    private lateinit var resultDialog : QrCodeResultDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_qr_scanner, container, false)
        initViews()
        onClicks()
        return mView.rootView
    }

    private fun initViews() {
        initializeQrScanner()
        setResultDialog()
    }

    private fun setResultDialog() {
        resultDialog = QrCodeResultDialog(context!!)
        resultDialog.setOnDismissListener(object : QrCodeResultDialog.OnDismissListener {
            override fun onDismiss() {
                scannerView.resumeCameraPreview(this@QrScannerFragment)
            }
        })
    }

    private fun onClicks() {
        mView.flashToggle.setOnClickListener {
            if(it.isSelected){
                offFlashLight()
            }else
                onFlashLight()
        }
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }

    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun initializeQrScanner(){
        scannerView = ZXingScannerView(context!!)
        scannerView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorTranslucent))
        scannerView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setLaserColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setAutoFocus(true)
        scannerView.setSquareViewFinder(true)
        scannerView.setResultHandler(this)
        mView.containerScanner.addView(scannerView)
        startQrCamera()
    }

    private fun startQrCamera(){
        scannerView.startCamera()
    }


    override fun onResume(){
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause(){
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy(){
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult?.text)
    }

    private fun onQrResult(text: String?) {
        if (text.isNullOrEmpty()) {
            Toast.makeText(context!!, "Empty Qr Code", Toast.LENGTH_SHORT).show()
        } else {
            saveToDatabase(text)
            resultDialog.show(text)
        }
    }

    private fun saveToDatabase(text: String?){
        val db = FirebaseFirestore.getInstance()
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance()
        val formattedDate = formatter.format(date)
        val location = text
        val time = Calendar.getInstance().time
        val formatter1 = SimpleDateFormat.getTimeInstance()
        val formattedTime = formatter1.format(time)

        val hm: HashMap<String, Any?>  = hashMapOf(
            "Date" to formattedDate,
            "Location" to location,
            "Time" to formattedTime
        )
        db.collection("Check In").add(hm).addOnSuccessListener { documentReference ->
            Log.d(Tag, "DocumentSnapshot written with ID: ${documentReference.id}")
        }.addOnFailureListener{ e ->
            Log.w(Tag, "Error adding document", e)

        }

    }

}