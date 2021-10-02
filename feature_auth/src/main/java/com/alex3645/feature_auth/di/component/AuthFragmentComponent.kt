package com.alex3645.feature_conference_list.di.component


import com.alex3645.feature_conference_list.di.module.AuthFragmentModule
import dagger.Component

@Component(modules = [AuthFragmentModule::class])
interface AuthFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): AuthFragmentComponent
    }
}