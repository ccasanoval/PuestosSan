package com.bancosantander.puestos.ui.viewModels.ownWorkstation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bancosantander.puestos.data.models.Workstation

class OwnWorkstationViewModel : ViewModel() {
    var currentWorkstation: MutableLiveData<Workstation>? = null
    fun getCurrentWorkstation(): LiveData<Workstation>? {
        return currentWorkstation
    }
    fun setCurrentWorkstation(workstation: Workstation){
        if (currentWorkstation == null) {
            currentWorkstation = MutableLiveData()
        }
        currentWorkstation?.value = workstation
    }
}