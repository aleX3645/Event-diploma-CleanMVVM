package com.alex3645.feature_conference_builder.di.components

import com.alex3645.feature_conference_builder.di.module.FragmentBuilderModule
import com.alex3645.feature_conference_builder.presentation.conferenceEditorListView.EventEditorListFragment
import dagger.Component

@Component(modules = [FragmentBuilderModule::class])
interface FragmentBuilderComponent {
    @Component.Factory
    interface Factory {
        fun create(): FragmentBuilderComponent
    }

    fun inject(eventEditorListFragment: EventEditorListFragment)
}