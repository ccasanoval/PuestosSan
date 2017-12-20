package com.bancosantander.puestos.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import kotlinx.android.synthetic.main.item_workstations_list.view.*

/**
 * Created by bangulo on 19/12/2017.
 */
class WorkstationsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    var workstations : List<Workstation> = arrayListOf()
    lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClickListener(view: View, workstation: Workstation)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workstations_list, parent, false)
        return WorkstationHolder(view, listener)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as WorkstationHolder
        viewHolder.bindItem(workstations[position])
    }
    override fun getItemCount(): Int = workstations.size

    fun setDataAndListener(list: List<Workstation>, callback: OnItemClickListener){
        workstations = list
        listener = callback
        notifyDataSetChanged()
    }

    class WorkstationHolder(itemView: View, callback: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        lateinit var workstation: Workstation
        init {
            itemView.llWorkstationItem.setOnClickListener{
                callback.onItemClickListener(itemView,workstation)
            }
        }

        fun bindItem(item: Workstation){
            workstation = item
            itemView.tvEstado.text = workstation.status.toString()
            itemView.tvPosicion.text = workstation.x.toString()+"º" + " " + workstation.y.toString()+"º"
            itemView.tvPropietario.text = workstation.idOwner
        }
    }




}