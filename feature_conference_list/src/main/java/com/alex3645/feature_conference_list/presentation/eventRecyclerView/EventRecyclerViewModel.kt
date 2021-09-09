package com.alex3645.feature_conference_list.presentation.eventRecyclerView

import android.telecom.Conference
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex3645.feature_conference_list.di.component.DaggerViewModelComponent
import com.alex3645.feature_conference_list.di.module.UseCaseModule
import com.alex3645.feature_conference_list.usecase.LoadConferencesUseCase
import javax.inject.Inject

class EventRecyclerViewModel: ViewModel() {

    init {
        DaggerViewModelComponent.factory().create(UseCaseModule()).inject(this)
        Log.d("!!!", loadConferencesUseCase.hashCode().toString())
    }

    val conferences: MutableLiveData<Conference> = MutableLiveData()

    @Inject
    lateinit var loadConferencesUseCase: LoadConferencesUseCase
}