package com.strelkovax.testcompanion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class ScreenActivity : AppCompatActivity() {
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(receiver, IntentFilter("finishScreenActivity"))
        val painView = PaintView(this)
        setContentView(painView)
    }

    override fun onResume() {
        super.onResume()
        val sp = getSharedPreferences("info", Context.MODE_PRIVATE)
        val ed = sp.edit()
        ed.putBoolean("isActive", true)
        ed.apply()
    }

    override fun onPause() {
        super.onPause()
        val sp = getSharedPreferences("info", Context.MODE_PRIVATE)
        val ed = sp.edit()
        ed.putBoolean("isActive", false)
        ed.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        val sp = getSharedPreferences("info", Context.MODE_PRIVATE)
        val ed = sp.edit()
        ed.putBoolean("isActive", false)
        ed.apply()
    }
}