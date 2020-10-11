package com.example.qrcodescannerapplication.ui.ui.dialog

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.PopupMenu
import android.widget.Toast
import com.example.qrcodescannerapplication.R
import com.example.qrcodescannerapplication.ui.ui.utils.toFormattedDisplay
import kotlinx.android.synthetic.main.layout_qr_result_show.*

class QrCodeResultDialog(var context : Context) {

    private lateinit var dialog : Dialog
    private var onDismissListener : OnDismissListener? = null


    init{
        initDialog()
    }

    private fun initDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_qr_result_show)
        dialog.setCancelable(false)
        onClicks()
    }

    fun setOnDismissListener(dismissListener: OnDismissListener){
        this.onDismissListener = dismissListener
    }

    private fun onClicks() {
        dialog.favouriteIcon.setOnClickListener {

        }

        dialog.shareResult.setOnClickListener {
            shareResult()
        }

        dialog.copyResult.setOnClickListener {
            copyResultToClipboard()
        }

        dialog.cancelDialog.setOnClickListener {
            dialog.dismiss()
            onDismissListener?.onDismiss()
        }
    }

    private fun shareResult() {
        val txtIntent = Intent(Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(Intent.EXTRA_TEXT,dialog.scannedText.text.toString())
        context.startActivity(txtIntent)
    }

    private fun copyResultToClipboard() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("QrScannerResult", dialog.scannedText.text)
        clipboard.text = clip.getItemAt(0).text.toString()
        Toast.makeText(context, "Copied to clipboard.", Toast.LENGTH_SHORT).show()
    }

    interface OnDismissListener{
        fun onDismiss()
    }
}