package com.bancosantander.puestos.ui.activities.searchUser

import android.os.Bundle
import com.bancosantander.puestos.R
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.Snackbar
import android.widget.ArrayAdapter
import com.bancosantander.puestos.ui.presenters.SearchUserPresenter
import com.bancosantander.puestos.ui.views.SearchUserViewContract
import kotlinx.android.synthetic.main.activity_search_user.*


class SearchUserActivity : BaseMvpActivity<SearchUserViewContract.View,
        SearchUserPresenter>(),
        SearchUserViewContract.View {

    private val os = arrayOf("Android", "Windows Vista", "Windows 7", "Windows 8", "Ubuntu 12.04", "Ubuntu 12.10", "Mac OSX", "iOS 5", "iOS 6", "Solaris", "Kubuntu", "Suse")

    override  var mPresenter: SearchUserPresenter = SearchUserPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)
        mPresenter.init()
        var adapter :ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,os)
        autoCompleteTextView.threshold = 3
        autoCompleteTextView.setAdapter(adapter)
    }


    override fun showLoading() {
        super.showLoadingDialog()
    }

    override fun hideLoading() {
        super.hideLoadingDialog()
    }

    override fun showSuccess() {
        Snackbar.make(llMain,"OK", Snackbar.LENGTH_LONG).show()
    }

    override fun showError(error: String) {
        Snackbar.make(llMain,error,Snackbar.LENGTH_LONG).show()
    }

}