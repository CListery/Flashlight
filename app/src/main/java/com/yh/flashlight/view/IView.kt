package com.yh.flashlight.view

import android.content.Context
import com.yh.flashlight.model.FlashlightState

/**
 * Created by Clistery on 18-7-24.
 */
interface IView {

    fun getContext(): Context

    fun updateUI(state: FlashlightState)
}