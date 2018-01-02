package com.bancosantander.puestos.ui.activities.tutorial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.ui.presenters.MainPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.R.id.edit
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import com.bancosantander.puestos.ui.dialogs.ChangePassDialog
import com.bancosantander.puestos.ui.presenters.TutorialPresenter
import com.bancosantander.puestos.ui.views.TutorialViewContract
import kotlinx.android.synthetic.main.activity_configuration.*
import android.widget.ImageView.ScaleType
import kotlinx.android.synthetic.main.activity_tutorial.*
import technolifestyle.com.imageslider.FlipperLayout
import technolifestyle.com.imageslider.FlipperView


class TutorialActivity : BaseMvpActivity<TutorialViewContract.View,
        TutorialPresenter>(),
        TutorialViewContract.View {

    override  var mPresenter: TutorialPresenter = TutorialPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        setUpTutorial()
        ivCloseTuto.setOnClickListener { finish() }
    }

    private fun setUpTutorial() {
        val flipperLayout = findViewById<View>(R.id.flipper_layout) as FlipperLayout
        val num_of_pages = 3
        for (i in 0 until num_of_pages) {
            val view = FlipperView(this)
                    .setImageDrawable(R.drawable.silla) // Use one of setImageUrl() or setImageDrawable() functions, otherwise IllegalStateException will be thrown
                    .setImageScaleType(ScaleType.CENTER_CROP) //You can use any ScaleType
                    .setDescription("Description")
            flipperLayout.scrollTimeInSec = 0
                    flipperLayout.addFlipperView(view)
        }

    }



}