package com.yh.flashlight.model

import android.content.Context
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import com.yh.flashlight.presenter.IPresenter

/**
 * Created by Clistery on 18-7-24.
 */
class FlashlightController private constructor(private val mPresenter: IPresenter) : IFlashlightStateManager {

    companion object {
        private var mInstance: FlashlightController? = null

        fun getInstance(presenter: IPresenter): FlashlightController {
            if (null == mInstance) {
                mInstance = FlashlightController(presenter)
            }
            return mInstance!!
        }
    }

    private val mHandler: Handler
    private val mThread: HandlerThread = HandlerThread("FL-thread")

    private val OPEN_OUT_OF_TIME: Long = 3 * 60 * 1000

    private var mCamera: Camera? = null

    private lateinit var mCameraManager: CameraManager
    private lateinit var mCameraIds: Array<out String>

    init {
        mThread.start()
        mHandler = Handler(mThread.looper)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraManager = mPresenter.getContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
            mCameraIds = mCameraManager.cameraIdList
        }
    }

    private fun notify(state: FlashlightState) {
        mPresenter.notify(state)
    }

    override fun openFlashlight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraManager.setTorchMode(mCameraIds[0], true)
        } else {
            mCamera = Camera.open()
            mCamera!!.startPreview()
            val parameter = mCamera!!.parameters
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                parameter.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            } else {
                parameter.set("flash-mode", "torch")
            }
            mCamera!!.parameters = parameter
            notify(FlashlightState.STATE_OPEN)
        }
        mHandler.removeCallbacksAndMessages(null)
        mHandler.postDelayed({
            closeFlashlight()
        }, OPEN_OUT_OF_TIME)
    }

    override fun closeFlashlight() {
        mHandler.removeCallbacksAndMessages(null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraManager.setTorchMode(mCameraIds[0], false)
        } else {
            if (null == mCamera) {
                notify(FlashlightState.STATE_CLOSE)
                return
            }
            val parameter = mCamera!!.parameters
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                parameter.flashMode = Camera.Parameters.FLASH_MODE_OFF
            } else {
                parameter.set("flash-mode", "off")
            }
            mCamera!!.parameters = parameter
            mCamera!!.stopPreview()
            mCamera!!.release()
            mCamera = null
            notify(FlashlightState.STATE_CLOSE)
        }
    }
}