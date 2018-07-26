package com.yh.flashlight.notify

import com.yh.flashlight.model.FlashlightState

/**
 * Created by Clistery on 18-7-24.
 */
interface INotification {

    fun showNotification(state: FlashlightState)

    fun showAlert()

    fun cancelAll()

}