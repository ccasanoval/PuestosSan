package com.bancosantander.puestos.ui.fragments

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.adapters.WorkstationsListAdapter
import com.bancosantander.puestos.ui.dialogs.InfoDialog
import com.bancosantander.puestos.ui.dialogs.InfoScreenDialog
import com.bancosantander.puestos.ui.dialogs.SiNoDialog
import com.bancosantander.puestos.ui.presenters.WorkstationsPresenter
import com.bancosantander.puestos.ui.presenters.fragments.WorkstationsListFragmentPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.bancosantander.puestos.ui.views.WorkstationsViewContract
import com.bancosantander.puestos.ui.views.WorkstationsViewFragmentContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.tutorial_fragment.*
import kotlinx.android.synthetic.main.workstations_list_fragment.*

/**
 * Created by bangulo on 19/12/2017.
 */
class TutorialFragment : Fragment(){

    private var imageTuto: Int = 0
    companion object {
        fun newInstance(imageTuto :Int): TutorialFragment {

            val fragment = TutorialFragment()
            val bundle = Bundle()
            bundle.putInt("image", imageTuto)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageTuto = arguments.getInt("image")
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.tutorial_fragment,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.with(activity).load(imageTuto).into(ivTutorial)
    }
}