package com.bancosantander.puestos.ui.views

import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView

/**
 * Created by mbalduciel on 16/12/17.
 */

object TutorialViewContract {

    interface View: BaseMvpView {

    }
    interface Presenter : BaseMvpPresenter<View> {

    }
}