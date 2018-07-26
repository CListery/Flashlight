package com.yh.flashlight

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yh.flashlight.presenter.FlashlightP

/**
 * Created by Clistery on 18-7-26.
 */
class FlashlightReceiver : BroadcastReceiver() {

    companion object {
        public const val ACTION_DEL: String = "action_del"
        public const val ACTION_CLICK: String = "action_click"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_DEL -> {
                FlashlightP.getInstance(FlashlightApp.get()).destroy()
            }
            ACTION_CLICK -> {
                FlashlightP.getInstance(FlashlightApp.get()).change()
            }
        }
    }
}