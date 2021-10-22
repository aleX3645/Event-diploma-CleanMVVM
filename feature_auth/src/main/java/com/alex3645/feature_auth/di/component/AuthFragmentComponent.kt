package com.alex3645.feature_auth.di.component


import com.alex3645.feature_auth.di.module.AuthFragmentModule
import dagger.Component

@Component(modules = [AuthFragmentModule::class])
interface AuthFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): AuthFragmentComponent
    }
}