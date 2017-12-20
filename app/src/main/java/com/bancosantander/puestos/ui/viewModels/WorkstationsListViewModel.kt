package com.bancosantander.puestos.ui.viewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bancosantander.puestos.data.models.Workstation

/**
 * Created by bangulo on 20/12/2017.
 */

class WorkstationsListViewModel : ViewModel(){

    var workstationsList : MutableLiveData<ArrayList<Workstation>>? = null
    get() = if(field != null) field else MutableLiveData()

}