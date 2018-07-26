package com.yh.flashlight

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.IntentFilter
import com.yh.flashlight.presenter.FlashlightP

/**
 * Created by Clistery on 18-7-26.
 */
class FlashlightApp : Application(), ComponentCallbacks2 {

    private lateinit var mReceiver: FlashlightReceiver

    companion object {
        private lateinit var mInstance: FlashlightApp
        fun get(): FlashlightApp {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this

        val intentF = IntentFilter()
        intentF.addAction(FlashlightReceiver.ACTION_CLICK)
        intentF.addAction(FlashlightReceiver.ACTION_DEL)

        //Follow the application life cycle
        mReceiver = FlashlightReceiver()
        registerReceiver(mReceiver, intentF)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        try {
            FlashlightP.getInstance(this).destroy()
            unregisterReceiver(mReceiver)
        } catch (e: Exception) {
        }
    }

}