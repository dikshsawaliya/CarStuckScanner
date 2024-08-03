package com.diksh.qrcarscanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {

    private val REQUEST_CAMERA_PERMISSION = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etPhoneNumber = findViewById<EditText>(R.id.etPhoneNumber)
        val btnGenerateQRCode = findViewById<Button>(R.id.btnGenerateQRCode)
        val ivQRCode = findViewById<ImageView>(R.id.ivQRCode)
        val btnStartScanner = findViewById<Button>(R.id.btnStartScanner)

        btnGenerateQRCode.setOnClickListener {
            val phoneNumber = etPhoneNumber.text.toString()
            if (phoneNumber.length == 10) {
                generateQRCode(phoneNumber, ivQRCode)
            } else {
                etPhoneNumber.error = "Please enter a valid 10-digit phone number"
            }
        }

        btnStartScanner.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            } else {
                startQRScanner()
            }
        }
    }

    private fun generateQRCode(phoneNumber: String, imageView: ImageView) {
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix = qrCodeWriter.encode(phoneNumber, BarcodeFormat.QR_CODE, 200, 200)
            val bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565)
            for (x in 0 until 200) {
                for (y in 0 until 200) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) ContextCompat.getColor(this, android.R.color.black) else ContextCompat.getColor(this, android.R.color.white))
                }
            }
            imageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    private fun startQRScanner() {
        val intent = Intent(this, QRScannerActivity::class.java)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startQRScanner()
            } else {
                // Handle permission denial
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
