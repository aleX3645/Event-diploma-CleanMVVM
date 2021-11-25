package com.alex3645.feature_conference_list.di.module

import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.recyclerView.ConferenceAdapter
import com.alex3645.feature_conference_list.usecase.LoadNextConferencesUseCase
import com.alex3645.feature_conference_list.usecase.LoadPictureByUrlUseCase
import dagger.Module
import dagger.Provides

@Module
class ConferenceListFragmentModule {
    @Provides
    fun provideLoadPictureByUrlUseCase(): LoadPictureByUrlUseCase{
        return LoadPictureByUrlUseCase()
    }

    @Provides
    fun provideConferenceAdapter(loadPictureByUrlUseCase: LoadPictureByUrlUseCase): ConferenceAdapter {
        return ConferenceAdapter(loadPictureByUrlUseCase)
    }
}