package com.bancosantander.puestos.ui.activities.searchUser

import android.os.Bundle
import com.bancosantander.puestos.R
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.ui.adapters.SearchUserAdapter
import com.bancosantander.puestos.ui.presenters.SearchUserPresenter
import com.bancosantander.puestos.ui.views.SearchUserViewContract
import kotlinx.android.synthetic.main.activity_search_user.*


class SearchUserActivity : BaseMvpActivity<SearchUserViewContract.View,
        SearchUserPresenter>(),
        SearchUserViewContract.View {

    private val os = arrayOf("Mikel", "Cesar", "Borja", "Fran", "Ubuntu 12.04", "Ubuntu 12.10", "Mac OSX", "iOS 5", "iOS 6", "Solaris", "Kubuntu", "Suse")

    override  var mPresenter: SearchUserPresenter = SearchUserPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)
        var adapter  = SearchUserAdapter(this,R.layout.activity_search_user,R.id.lbl_searched_name, mutableListOf())
        autoCompleteTextView.threshold = 3
        autoCompleteTextView.setAdapter(adapter)
        mPresenter.getUsers()
    }


    override fun setMyAdapter(listUser: MutableList<User>) {
        autoCompleteTextView.setAdapter(SearchUserAdapter(this,R.layout.activity_search_user,R.id.lbl_searched_name,listUser))
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