package com.yh.flashlight.view

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import com.yh.flashlight.R
import com.yh.flashlight.model.FlashlightState
import com.yh.flashlight.presenter.FlashlightP
import com.yh.flashlight.presenter.IPresenter

/**
 * Created by Clistery on 18-7-24.
 */
class FlashlightView : ConstraintLayout, IView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mFlashBg: ImageView = ImageView(context)
    private val mFlashBtn: ImageView = ImageView(context)

    private var mIPresenter: IPresenter

    init {
        mFlashBg.setImageResource(R.drawable.bg_fl)
        val bgLp = LayoutParams(0, 0)
        bgLp.startToStart = LayoutParams.PARENT_ID
        bgLp.endToEnd = LayoutParams.PARENT_ID
        bgLp.topToTop = LayoutParams.PARENT_ID
        bgLp.bottomToBottom = LayoutParams.PARENT_ID
        addView(mFlashBg, bgLp)

        mFlashBtn.setImageResource(R.drawable.bg_btn)
        val btnLp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        btnLp.startToStart = LayoutParams.PARENT_ID
        btnLp.endToEnd = LayoutParams.PARENT_ID
        btnLp.topToTop = LayoutParams.PARENT_ID
        btnLp.bottomToBottom = LayoutParams.PARENT_ID
        btnLp.verticalBias = 0.85f
        addView(mFlashBtn, btnLp)

        setBackgroundColor(Color.parseColor("#FF181818"))

        mIPresenter = FlashlightP.getInstance(this)

        initEvents()

    }

    private fun initEvents() {
        mFlashBtn.setOnClickListener {
            mIPresenter.change()
        }
    }

    override fun updateUI(state: FlashlightState) = when (state) {
        FlashlightState.STATE_CLOSE -> {
            mFlashBg.isSelected = false
            mFlashBtn.isSelected = false
        }
        FlashlightState.STATE_OPEN -> {
            mFlashBg.isSelected = true
            mFlashBtn.isSelected = true
        }
    }
}