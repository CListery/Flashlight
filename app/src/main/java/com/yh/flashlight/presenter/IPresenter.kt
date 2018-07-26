package com.yh.flashlight.presenter

import android.content.Context
import com.yh.flashlight.model.FlashlightState

/**
 * Created by Clistery on 18-7-24.
 */
interface IPresenter {

    fun getContext(): Context

    fun change()

    fun notify(state: FlashlightState)

    fun destroy()

}