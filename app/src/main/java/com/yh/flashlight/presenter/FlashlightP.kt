package com.yh.flashlight.presenter

import android.content.Context
import com.yh.flashlight.FlashlightApp
import com.yh.flashlight.model.FlashlightController
import com.yh.flashlight.model.FlashlightState
import com.yh.flashlight.model.IFlashlightStateManager
import com.yh.flashlight.notify.INotification
import com.yh.flashlight.notify.NotificationControl
import com.yh.flashlight.view.IView

/**
 * Created by Clistery on 18-7-24.
 */
class FlashlightP(private val mIView: IView) : IPresenter {
    companion object {
        private var mInstance: FlashlightP? = null

        fun getInstance(iView: Any): IPresenter {
            when (iView) {
                is IView -> {
                    if (null == mInstance) {
                        mInstance = FlashlightP(iView)
                    }
                }
                is FlashlightApp -> {
                    return mInstance!!
                }
                else -> {
                    throw Exception("Not support type: $iView")
                }
            }
            return mInstance!!
        }
    }

    private val mIFlashlightStateManager: IFlashlightStateManager
    private val mINotifier: INotification

    private lateinit var mCurrentFlashState: FlashlightState

    init {
        mIFlashlightStateManager = FlashlightController.getInstance(this)
        mINotifier = NotificationControl.getInstance(mIView.getContext())
        mIFlashlightStateManager.closeFlashlight()
    }

    override fun getContext(): Context {
        return mIView.getContext()
    }

    override fun notify(state: FlashlightState) {
        mCurrentFlashState = state
        mIView.updateUI(state)
        mINotifier.showNotification(state)
    }

    override fun change() {
        when (mCurrentFlashState) {
            FlashlightState.STATE_OPEN -> {
                mIFlashlightStateManager.closeFlashlight()
            }
            FlashlightState.STATE_CLOSE -> {
                mIFlashlightStateManager.openFlashlight()
            }
        }
    }

    override fun destroy() {
        mIFlashlightStateManager.closeFlashlight()
        mINotifier.cancelAll()
    }
}
