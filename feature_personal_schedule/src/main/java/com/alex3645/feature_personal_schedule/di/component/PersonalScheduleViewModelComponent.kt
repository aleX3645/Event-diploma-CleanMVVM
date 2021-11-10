package com.alex3645.feature_personal_schedule.di.component

import com.alex3645.feature_personal_schedule.di.module.PersonalScheduleViewModelModule
import com.alex3645.feature_personal_schedule.presentation.personalScheduleView.PersonalScheduleViewModel
import dagger.Component

@Component(modules = [PersonalScheduleViewModelModule::class])
interface PersonalScheduleViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): PersonalScheduleViewModelComponent
    }

    fun inject(personalScheduleViewModel: PersonalScheduleViewModel)
}