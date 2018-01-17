package com.bancosantander.puestos.ui.activities.searchUser

import android.content.Context
import android.os.Bundle
import com.bancosantander.puestos.R
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.ui.adapters.SearchUserAdapter
import com.bancosantander.puestos.ui.presenters.SearchUserPresenter
import com.bancosantander.puestos.ui.views.SearchUserViewContract
import kotlinx.android.synthetic.main.activity_search_user.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v7.widget.Toolbar
import android.view.inputmethod.InputMethodManager
import com.bancosantander.puestos.util.presentation
import kotlinx.android.synthetic.main.activity_own_workstation.*
import org.jetbrains.anko.contentView
import java.util.*


class SearchUserActivity : BaseMvpActivity<SearchUserViewContract.View,
        SearchUserPresenter>(),
        SearchUserViewContract.View {

    override  var mPresenter: SearchUserPresenter = SearchUserPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)
        val viewActivity = contentView
        setupToolbar()
        var adapter  = SearchUserAdapter(this,R.layout.activity_search_user,R.id.lbl_searched_name, mutableListOf())
        autoCompleteTextView.threshold = 3
        autoCompleteTextView.setAdapter(adapter)
        mPresenter.getUsers()

        autoCompleteTextView.setOnItemClickListener{ parent: AdapterView<out Adapter?>?, view: View?, position: Int, id: Long ->
            printUserView(parent?.getItemAtPosition(position) as User)
            autoCompleteTextView.clearFocus()
            if (viewActivity != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(viewActivity.windowToken, 0)
            }
        }

        ivCloseUserSearched.setOnClickListener {
            hideUserView()
            autoCompleteTextView.setText("")
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    private fun printUserView(user: User) {
        tvChannelSearched.text = user.channel
        tvFullNameSearched.text = user.fullname
        tvEmailSearched.text = user.email
        cvUserSearched.visibility = View.VISIBLE
        if (user.type.equals(User.Type.Fixed)){
            tvWorkstationNameSearched.text = "Puesto: ${user.workstation}"
            llWorkstationSearched.visibility = View.VISIBLE
        } else{
            llWorkstationSearched.visibility = View.GONE
        }

    }

    private fun hideUserView(){
        cvUserSearched.visibility = View.GONE
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}