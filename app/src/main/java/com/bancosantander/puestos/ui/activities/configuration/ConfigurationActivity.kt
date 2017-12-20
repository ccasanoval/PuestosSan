package com.bancosantander.puestos.ui.activities.configuration

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import com.bancosantander.puestos.R
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
        btnChangePassword.setOnClickListener{mPresenter.changePassword()}
    }

    override fun changePassword() {
        changePasswordDialog()
    }
    fun changePasswordDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_change_password, null)

        val dialog = dialogBuilder
                .setView(dialogView)
                .setTitle("Cambiar contraseña")
                .setMessage("Inserta la nueva contraseña")
                .setPositiveButton("Cambiar", { dialog, whichButton ->
                    mPresenter.callToChangePassword(dialogView.etPassword.text.toString())
                    dialog.dismiss()
                })
                .setNegativeButton("Cancelar", { dialog, whichButton ->
                    dialog.dismiss()
                })
                .create()
        dialog.show()
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


}
