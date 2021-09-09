package com.alex3645.feature_conference_list.di.component

import com.alex3645.feature_conference_list.di.module.UseCaseModule
import com.alex3645.feature_conference_list.di.scope.ConfListModule
import com.alex3645.feature_conference_list.presentation.eventRecyclerView.EventRecyclerViewModel
import dagger.Component

@Component(modules = [UseCaseModule::class])
@ConfListModule
interface ViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(useCaseModule: UseCaseModule): ViewModelComponent
    }

    fun inject(ConfListViewModel: EventRecyclerViewModel)
}