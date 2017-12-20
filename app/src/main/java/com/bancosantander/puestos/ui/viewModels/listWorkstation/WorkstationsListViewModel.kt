package com.bancosantander.puestos.ui.viewModels.listWorkstation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bancosantander.puestos.data.models.Workstation

/**
 * Created by bangulo on 20/12/2017.
 */

class WorkstationsListViewModel : ViewModel(){

    var workstationsList : MutableLiveData<ArrayList<Workstation>>? = null
    fun getWorkstationsList(): LiveData<ArrayList<Workstation>> {
        if (workstationsList == null) {
            workstationsList = MutableLiveData()
            workstationsList?.value = arrayListOf()
        }
        return workstationsList!!
    }



}