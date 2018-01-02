package com.bancosantander.puestos.ui.activities.main

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
import kotlinx.android.synthetic.main.activity_configuration.*


class MainActivity : BaseMvpActivity<MainViewContract.View,
        MainPresenter>(),
        MainViewContract.View {


    override  var mPresenter: MainPresenter = MainPresenter()
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.init()
        prefs = getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE)

        checkIfIsFirstLogin()

        setupView()

        btnManage.setOnClickListener { mPresenter.goToManageOwnWorkstation() }
        btnFill.setOnClickListener { mPresenter.goToFillWorkstation() }
        btnLogout.setOnClickListener { mPresenter.logout() }
        btnConfiguration.setOnClickListener{mPresenter.gotToConfiguration()}
    }

    private fun setupView() {
        tvEmail.text = mPresenter.getEmail()
    }

    private fun checkIfIsFirstLogin() {
        if(!prefs.getBoolean(mPresenter.auth().getEmail(),false)){
            showChangePassDialog()
        }
    }

    private fun showChangePassDialog() {
        ChangePassDialog.newInstance(activity=this,title=getString(R.string.change_pass_first_time),callback={oldPass, newPass ->
            mPresenter.callToChangePassword(oldPass,newPass)
        })
        val editor = prefs.edit()
        editor.putBoolean(mPresenter.auth().getEmail(),true)
        editor.commit()
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
        val PREFERENCES = "puestos_login_preferences"
    }

    override fun showLoading() {
        super.showLoadingDialog()
    }

    override fun hideLoading() {
        super.hideLoadingDialog()
    }

    override fun showSuccess() {
        Snackbar.make(llMain,"Contraseña cambiada correctamente", Snackbar.LENGTH_LONG).show()
    }

    override fun showError(error: String) {
        Snackbar.make(llMain,error,Snackbar.LENGTH_LONG).show()
    }

    override fun showErrorMinLenght() {
        Snackbar.make(llMain,"Contraseña no valida, longitud minima 6 caracteres",Snackbar.LENGTH_SHORT).show()
    }

    override fun disableWorkstationList() {
        ivFill.setColorFilter(ContextCompat.getColor(this,R.color.very_light_grey), android.graphics.PorterDuff.Mode.MULTIPLY)
        tvFill.setTextColor(ContextCompat.getColor(this,R.color.very_light_grey))
        btnFill.isClickable = false
    }

    override fun enableWorkstationList() {
        ivFill.clearColorFilter()
        tvFill.setTextColor(ContextCompat.getColor(this,R.color.light_grey))
        btnFill.isClickable = true
    }

    override fun showTutorial() {
        mPresenter.goToTutorial()
        finish()
    }
}