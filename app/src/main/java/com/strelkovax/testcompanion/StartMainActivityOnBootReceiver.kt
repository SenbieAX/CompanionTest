package com.strelkovax.testcompanion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StartMainActivityOnBootReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == p1?.action) {
            val serviceIntent = Intent(p0, MainActivity::class.java)
            serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            p0?.startActivity(serviceIntent)
        }
    }
}