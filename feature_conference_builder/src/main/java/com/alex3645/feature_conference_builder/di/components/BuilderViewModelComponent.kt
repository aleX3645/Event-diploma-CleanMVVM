package com.alex3645.feature_conference_builder.di.components

import com.alex3645.feature_conference_builder.di.module.BuilderViewModelModule
import com.alex3645.feature_conference_builder.presentation.conferenceEditorView.ConferenceEditorViewModel
import com.alex3645.feature_conference_builder.presentation.eventEditorView.EventEditorViewModel
import dagger.Component

@Component(modules = [BuilderViewModelModule::class])
interface BuilderViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(builderViewModelModule: BuilderViewModelModule): BuilderViewModelComponent
    }

    fun inject(conferenceEditorViewModel: ConferenceEditorViewModel)
    fun inject(eventEditorViewModel: EventEditorViewModel)
}