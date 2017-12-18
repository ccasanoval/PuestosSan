package com.bancosantander.puestos.ui.activities.workstations

import android.os.Bundle
import android.os.PersistableBundle
import com.bancosantander.puestos.R
import com.bancosantander.puestos.ui.presenters.WorkstationsPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity

/**
 * Created by bangulo on 18/12/2017.
 */
class WorkstationsActivity : BaseMvpActivity<MainViewContract.View,
        WorkstationsPresenter>(),
        MainViewContract.View {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.workstations_activity)
        mPresenter.init()
    }

    override var mPresenter: WorkstationsPresenter  = WorkstationsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}