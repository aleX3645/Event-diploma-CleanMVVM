package com.alex3645.feature_personal_schedule.di.component

import com.alex3645.feature_personal_schedule.di.module.PersonalScheduleFragmentModule
import com.alex3645.feature_personal_schedule.presentation.personalScheduleView.PersonalScheduleFragment
import dagger.Component

@Component(modules = [PersonalScheduleFragmentModule::class])
interface PersonalScheduleFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): PersonalScheduleFragmentComponent
    }

    fun inject(personalScheduleFragment: PersonalScheduleFragment)

}