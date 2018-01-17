package com.bancosantander.puestos.ui.viewModels.map

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bancosantander.puestos.data.models.Workstation

class OwnWorkstationMapViewModel : ViewModel() {
    var currentWorkstation: MutableLiveData<Workstation>? = null
}