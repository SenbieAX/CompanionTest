package com.strelkovax.testcompanion

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView

class OverlayService : Service() {

    private lateinit var params: WindowManager.LayoutParams
    private lateinit var overlayImage: ImageView
    private lateinit var windowManager: WindowManager

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        overlayImage = ImageView(this)
        overlayImage.setImageResource(R.drawable.cloud)
        overlayImage.setOnClickListener {
            val sp =
                getSharedPreferences("info", Context.MODE_PRIVATE).getBoolean("isActive", false)
            if (!sp) {
                val dialogIntent = Intent(this, ScreenActivity::class.java)
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(dialogIntent)
            } else {
                sendBroadcast(Intent("finishScreenActivity"))
            }
        }

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.END
        params.x = 25
        params.y = 25

        windowManager.addView(overlayImage, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(overlayImage)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}