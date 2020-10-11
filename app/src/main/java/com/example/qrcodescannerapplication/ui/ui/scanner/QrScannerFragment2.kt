package com.example.qrcodescannerapplication.ui.ui.scanner

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.qrcodescannerapplication.R
import com.example.qrcodescannerapplication.ui.ui.dialog.QrCodeResultDialog
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_qr_scanner.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


/**
 * A simple [Fragment] subclass.
 * Use the [QrScannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QrScannerFragment2 : Fragment(), ZXingScannerView.ResultHandler {
    companion object {
        fun newInstance(): QrScannerFragment2{
            return QrScannerFragment2()
        }
    }

    private lateinit var mView: View

    private lateinit var scannerView : ZXingScannerView

    private lateinit var resultDialog : QrCodeResultDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_qr_scanner, container, false)
        initViews()
        //onClicks()
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
                scannerView.resumeCameraPreview(this@QrScannerFragment2)
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

    private fun onQrResult(text: String?){
        if(text.isNullOrEmpty()){
            Toast.makeText(context!!, "Empty Qr Code", Toast.LENGTH_SHORT).show()
        }else{
            //checkResult(rawResult:Result)
        }
    }

    private fun checkResult(rawResult:Result) {

    }

}