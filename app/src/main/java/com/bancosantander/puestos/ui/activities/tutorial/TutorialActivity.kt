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
import com.bancosantander.puestos.ui.adapters.TutorialAdapter
import kotlinx.android.synthetic.main.activity_tutorial.*


class TutorialActivity : BaseMvpActivity<TutorialViewContract.View,
        TutorialPresenter>(),
        TutorialViewContract.View {

    override  var mPresenter: TutorialPresenter = TutorialPresenter()
    private lateinit var pagerAdapter: TutorialAdapter
    private lateinit var images: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        setUpTutorial()
        ivCloseTuto.setOnClickListener { finish() }
    }

    private fun setUpTutorial() {
        images = arrayListOf(
                R.drawable.main_tutorial,
                R.drawable.own_tutorial,
                R.drawable.list_tutorial,
                R.drawable.map_tutorial,
                R.drawable.calendar_tutorial
        )
        pagerAdapter = TutorialAdapter(supportFragmentManager, images)
        vpTutorial.adapter = pagerAdapter


    }



}