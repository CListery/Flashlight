package com.yh.flashlight.notify

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.Toast
import com.yh.flashlight.FlashlightReceiver
import com.yh.flashlight.FlashlightReceiver.Companion.ACTION_CLICK
import com.yh.flashlight.FlashlightReceiver.Companion.ACTION_DEL
import com.yh.flashlight.R
import com.yh.flashlight.model.FlashlightState


/**
 * Created by Clistery on 18-7-24.
 */
class NotificationControl(private val mCtx: Application) : INotification {

    companion object {
        private const val NOTIFY_CHANNEL_NAME = "手电筒"
        private const val NOTIFY_CHANNEL_ID = "flashlight_state"

        public const val DEL_REQUEST_CODE: Int = 0x121
        public const val CLICK_REQUEST_CODE: Int = 0x122

        private var mInstance: NotificationControl? = null

        fun getInstance(context: Context): NotificationControl {
            if (null == mInstance) {
                mInstance = NotificationControl(context.applicationContext as Application)
            }
            return mInstance!!
        }
    }

    private var mNotifyManager: NotificationManagerCompat = NotificationManagerCompat.from(mCtx)

    private var mNotify: NotificationCompat.Builder? = null

    override fun showNotification(state: FlashlightState) {
        mNotifyManager.cancelAll()
        mNotify = makeNotify()
        mNotify!!.setContentText(state.msg)
        mNotify!!.setDeleteIntent(PendingIntent.getBroadcast(mCtx, DEL_REQUEST_CODE, Intent(ACTION_DEL), PendingIntent.FLAG_CANCEL_CURRENT))
        mNotify!!.setContentIntent(PendingIntent.getBroadcast(mCtx, CLICK_REQUEST_CODE, Intent(ACTION_CLICK), PendingIntent.FLAG_CANCEL_CURRENT))
        mNotifyManager.notify(NOTIFY_CHANNEL_NAME, 1, mNotify!!.build())
    }

    private fun makeNotify(): NotificationCompat.Builder {
        if (null != mNotify) {
            return mNotify!!
        }
        mNotify = NotificationCompat.Builder(mCtx, NOTIFY_CHANNEL_ID)
        mNotify!!.setContentTitle(mCtx.getString(R.string.app_name))
        mNotify!!.setSmallIcon(R.drawable.icon_state_normal)
        return mNotify!!
    }

    override fun showAlert() {
        Toast.makeText(mCtx, "闪光灯无法使用", Toast.LENGTH_SHORT).show()
    }

    override fun cancelAll() {
        mNotifyManager.cancelAll()
    }
}