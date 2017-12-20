package com.bancosantander.puestos.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.data.models.Workstation

/**
 * Created by bangulo on 19/12/2017.
 */
class WorkstationsListAdapter(val workstations : List<Workstation>) : RecyclerView.Adapter<WorkstationsListAdapter.ViewHolder>() {

    class ViewHolder(workstationView : View) : RecyclerView.ViewHolder(workstationView)

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}