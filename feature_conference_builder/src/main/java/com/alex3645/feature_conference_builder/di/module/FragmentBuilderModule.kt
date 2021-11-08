package com.alex3645.feature_conference_builder.di.module

import com.alex3645.feature_conference_builder.domain.model.Tariff
import com.alex3645.feature_conference_builder.presentation.eventEditorListView.recyclerView.EventAdapter
import com.alex3645.feature_conference_builder.presentation.tariffListView.recyclerView.TariffAdapter
import dagger.Module
import dagger.Provides

@Module
class FragmentBuilderModule {
    @Provides
    fun provideEventAdapter(): EventAdapter {
        return EventAdapter()
    }

    @Provides
    fun provideTariffAdapter(): TariffAdapter {
        return TariffAdapter()
    }
}