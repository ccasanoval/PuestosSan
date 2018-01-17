package com.bancosantander.puestos.ui.activities.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.bancosantander.puestos.R
import com.bancosantander.puestos.ui.presenters.MainPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import com.bancosantander.puestos.ui.dialogs.ChangePassDialog


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

        setupView()

        btnManage.setOnClickListener { mPresenter.goToManageOwnWorkstation() }
        btnFill.setOnClickListener {
            mPresenter.goToFillWorkstation()
        }
        btnLogout.setOnClickListener { mPresenter.logout() }
        btnConfiguration.setOnClickListener{mPresenter.gotToConfiguration()}
        btnSearchUser.setOnClickListener{mPresenter.goToSearchUser()}

		imgUser.setOnClickListener { mPresenter.goToMap() }
    }

    private fun setupView() {
        tvEmail.text = mPresenter.getEmail()
    }

    override fun hadChangedPass() {
        if(!mPresenter.hadChangedPass()){
            showChangePassDialog()
        }else {
            showTutorial()
        }
    }

    private fun showChangePassDialog() {
        ChangePassDialog.newInstance(activity=this,title=getString(R.string.change_pass_first_time),callback={oldPass, newPass ->
            mPresenter.callToChangePassword(oldPass,newPass)
            if(oldPass.equals("") || newPass.equals("")){
                mPresenter?.logout()
            }
        })
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

    override fun showWorkstationInMap() {
        tvFill.text = getString(R.string.map_position)
        ivFill.setImageDrawable(ContextCompat.getDrawable(this,android.R.drawable.ic_menu_mapmode))
    }

    override fun enableWorkstationList() {
        tvFill.text = getString(R.string.available_list)
        ivFill.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.list))
    }

    override fun showTutorial() {
        if (!prefs.getBoolean(mPresenter.auth().getEmail(), false)) {
            val editor = prefs.edit()
            editor.putBoolean(mPresenter.auth().getEmail(),true)
            editor.commit()
            mPresenter.goToTutorial()
        }

    }
}