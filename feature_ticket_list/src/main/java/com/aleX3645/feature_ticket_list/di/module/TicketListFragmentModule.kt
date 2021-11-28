package com.alex3645.feature_ticket_list.di.module

import com.alex3645.feature_ticket_list.presentation.ticketListView.recyclerView.TicketListAdapter
import dagger.Module
import dagger.Provides

@Module
class TicketListFragmentModule {
    @Provides
    fun provideEventAdapter(): TicketListAdapter {
        return TicketListAdapter()
    }
}