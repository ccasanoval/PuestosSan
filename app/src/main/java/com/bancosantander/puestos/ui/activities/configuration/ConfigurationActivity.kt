package com.bancosantander.puestos.ui.activities.configuration

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.ui.dialogs.ChangePassDialog
import com.bancosantander.puestos.ui.presenters.ConfigurationPresenter
import com.bancosantander.puestos.ui.views.ConfigurationViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_configuration.*
import kotlinx.android.synthetic.main.dialog_change_password.view.*


class ConfigurationActivity :  BaseMvpActivity<ConfigurationViewContract.View,
        ConfigurationPresenter>(),
        ConfigurationViewContract.View  {



    override  var mPresenter: ConfigurationPresenter = ConfigurationPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)
        mPresenter.init()
        setupToolbar()
        btnChangePassword.setOnClickListener{mPresenter.changePassword()}
    }

    override fun changePassword() {
        changePasswordDialog()
    }
    fun changePasswordDialog() {
       ChangePassDialog.newInstance(activity = this,callback={oldPass,newPass->
           mPresenter.callToChangePassword(oldPass,newPass)
       })
    }

    private fun setupToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }
    override fun showErrorMinLenght() {
        Snackbar.make(llConfigurationLayout,"Contraseña no valida, longitud minima 6 caracteres",Snackbar.LENGTH_SHORT).show()
    }
    override fun showSuccess() {
        Snackbar.make(llConfigurationLayout,"Contraseña cambiada correctamente",Snackbar.LENGTH_LONG).show()
    }

    override fun showError(error: String) {
        Snackbar.make(llConfigurationLayout,error,Snackbar.LENGTH_LONG).show()
    }

    override fun showLoading() {
        super.showLoadingDialog()
    }

    override fun hideLoading() {
        super.hideLoadingDialog()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
