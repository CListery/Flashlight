package com.yh.flashlight

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yh.flashlight.presenter.FlashlightP
import com.yh.flashlight.presenter.IPresenter
import com.yh.flashlight.view.FlashlightView

/**
 * Created by Clistery on 18-7-24.
 */
class FlashlightAct : AppCompatActivity() {

    private lateinit var mPresenter: IPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_flash_light)

        mPresenter = FlashlightP.getInstance(findViewById<FlashlightView>(R.id.flash_light_view))

    }

}